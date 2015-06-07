package wav.common.scalajs.macros

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import scala.scalajs.js
import scala.collection._

// Recursion not currently supported.
// for the low-down on macros http://www.slideshare.net/prasinous/scaladays-2013-final
// originally based off example: http://blog.echo.sh/post/65955606729/exploring-scala-macros-map-to-case-class-conversion
object JS {

  type TOJS = { val toJs: js.Object }

  implicit def apply[T]: T => js.Object = macro converterMacro[T]

  def converterMacro[T: c.WeakTypeTag](c: Context): c.Tree = {
    import c.universe._
    val tpe = c.weakTypeOf[T]
    val symToJs = typeOf[TOJS].typeSymbol
    val fields = tpe.decls.collectFirst {
      case m: MethodSymbol if m.isPrimaryConstructor => m
    }.get.paramLists.head
    val pieces: List[(Int, Tree)] = fields.map { field =>
      val name = field.asTerm.name
      val decoded = name.decodedName.toString
      val returnType = field.typeSignature
      if (returnType <:< typeOf[TOJS])
        (0, q"""$decoded -> (t: $symToJs).toJs""")
      else if (returnType <:< typeOf[Enumeration#Value])
        (0, q"""$decoded -> t.$name.toString()""")
      else if (returnType <:< typeOf[GenMap[String, _]] &&
        returnType.typeArgs(1) <:< typeOf[TOJS])
        (0, q"""$decoded -> t.$name.map((k: String, o: $symToJs) => (k, o.toJs)).toJSDictionary""")
      else if (returnType <:< typeOf[GenMap[String, _]])
        (0, q"""$decoded -> t.$name.toJSDictionary""")
      else if ((returnType <:< typeOf[GenTraversableOnce[_]] ||
                returnType <:< typeOf[Array[_]]) &&
                returnType.typeArgs(0) <:< typeOf[TOJS])
        (0, q"""$decoded -> t.$name.map((o: $symToJs) => o.toJs).toJSArray""")
      else if ((returnType <:< typeOf[GenTraversableOnce[_]] ||
                returnType <:< typeOf[Array[_]]))
        (0, q"""$decoded -> t.$name.toJSArray""")
      else if (returnType <:< typeOf[Option[_]] ||
            returnType <:< typeOf[js.UndefOr[_]]) {
        if (returnType.typeArgs(0) <:< typeOf[TOJS]) {
          (1, q"""t.$name.foreach(v => p.updateDynamic($decoded)(v.toJs))""")
        } else {
          (1, q"""t.$name.foreach(v => p.updateDynamic($decoded)(v))""")
        }
      }
      else
        (0, q"""$decoded -> t.$name""")
    }
    val vals = pieces.filter(t => t._1 == 0).map(_._2)
    val undefs = pieces.filter(t => t._1 == 1).map(_._2)
    q"""(t: $tpe) => {
         import scala.language.reflectiveCalls
         import scalajs.js.JSConverters._
         val p = scala.scalajs.js.Dynamic.literal(..$vals)
         ..$undefs
         scala.scalajs.js.Dynamic.global.console.log(p)
         p
      }
    """
  }
}