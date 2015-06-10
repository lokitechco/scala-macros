package wav.common.scalajs.macros

import scala.scalajs.js

object Test {

  val optionTest = JS[Option[Int]](Some(1))

  val seqTest = JS[Seq[Int]](Seq(1))

  val mapTest = JS[Map[Int, Double]](Map(1 -> 1)) // Not verified.

  object enum extends Enumeration {
    val x = Value
  }

  case class Simple(s: String, t: enum.Value)

  case class Simple2(s: String, t: Option[enum.Value])

  val simpleTest = JS[Simple]

  val simpleTest2 = JS[Simple2]

  case class SimpleOption(so: Option[String])

  val simpleOptionTest = JS[SimpleOption]

  case class SimpleSeq(ss: Seq[String])

  val simpleSeqTest = JS[SimpleSeq]

  case class SimpleMap(ss: Map[Double, String])

  val simpleMapTest = JS[SimpleMap]

  case class SimpleToJs(s: String) {
    val toJs: js.Object = JS[SimpleToJs](this)
  }

  case class SimpleToJsOpt(s: Option[String]) {
    val toJs: js.Object = JS[SimpleToJsOpt](this)
  }

  case class SimpleToJsDefault(s: Option[String] = None) {
    val toJs: js.Object = JS[SimpleToJsDefault](this)
  }

  case class Mixed(
    s: String,
    `do`: Option[Double],
    sim: SimpleToJs,
    simo: Option[SimpleToJs],
    id: Int = 1,
    sod: Option[String] = None,
    sud: js.UndefOr[js.Object] = js.undefined,
    sima: Array[SimpleToJs] = Array.empty) {
    val toJs: js.Object = JS[Mixed](this)
  }
  
}