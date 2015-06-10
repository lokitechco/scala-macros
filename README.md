Macros for use with scala/scalajs

## Usage

Add the following dependency to your `build.sbt`

```scala
resolvers ++= Seq(
    Resolver.url("wav", url("https://dl.bintray.com/wav/maven"))(Resolver.ivyStylePatterns))

libraryDependencies += "wav.common" %% "scala-macros" % "0.1.1"
```