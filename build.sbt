scalaVersion in ThisBuild := "2.11.6"

organization in ThisBuild := "wav.common"

name := "scalajs-macros"

version := "0.6.3.1-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.6"

libraryDependencies ++= Seq(
  "org.scala-js" %% "scalajs-library" % "0.6.3" % "compile",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value % "compile")

scalacOptions in ThisBuild ++= Seq(
  "-deprecation", "-unchecked", "-feature",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials")

updateOptions := updateOptions.value.withCachedResolution(true)