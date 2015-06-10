scalaVersion := "2.11.6"

organization := "wav.common"

description := "Macros for use with scala/scalajs"

licenses +=("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

name := "scala-macros"

version := "0.1.1-SNAPSHOT"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.scala-js" %% "scalajs-library" % "0.6.3" % "compile",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value % "compile")

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature","-language:existentials")

publishMavenStyle := false

bintrayReleaseOnPublish := false

publishArtifact in Test := false

publishArtifact in(Compile, packageSrc) := false

publishArtifact in(Compile, packageDoc) := false

bintrayRepository := "maven"

updateOptions := updateOptions.value.withCachedResolution(true)