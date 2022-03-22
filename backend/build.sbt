lazy val commonSettings = Seq(
  target := { baseDirectory.value / "target" }
)
lazy val root = project.in(file("."))
  .settings(
    commonSettings,
    name := "polytrade-api",
    version := "0.1.0",
    scalaVersion := "3.0.0-RC1",
    libraryDependencies ++= Seq(
    "com.typesafe.akka" % "akka-actor-typed_2.13" % "2.6.14",
    "com.typesafe.akka" % "akka-stream-typed_2.13" % "2.6.14",
    "com.typesafe.akka" % "akka-http_2.13" % "10.2.4",
    "com.typesafe.akka" % "akka-http-spray-json_2.13" % "10.2.4",
    )
  )