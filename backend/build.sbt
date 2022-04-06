lazy val commonSettings = Seq(
  target := { baseDirectory.value / "target" }
)
lazy val root = project.in(file("."))
  .settings(
    commonSettings,
    name := "polytrade-api",
    version := "0.1.0",
    scalaVersion := "2.12.15",
    libraryDependencies ++= Seq(
    "com.typesafe.akka" % "akka-actor-typed_2.12" % "2.6.14",
    "com.typesafe.akka" % "akka-stream-typed_2.12" % "2.6.14",
    "com.typesafe.akka" % "akka-http_2.12" % "10.2.7",
    "com.typesafe.akka" % "akka-http-spray-json_2.12" % "10.2.7",
    "org.reactivemongo" %% "reactivemongo" % "0.20.0", 
    "de.heikoseeberger" %% "akka-http-circe" % "1.39.2",
    "io.circe"          %% "circe-generic" % "0.14.1",
    "de.heikoseeberger" %% "akka-http-play-json" % "1.20.0",
    "com.github.jwt-scala" %% "jwt-json4s-native" % "9.0.5",
    "com.github.t3hnar" %% "scala-bcrypt" % "4.3.0",
    )
  )