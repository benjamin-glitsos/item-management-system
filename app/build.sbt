name := "Hospital MS"
version := "1.0"
scalaVersion := "2.13.0"

val http4sVersion = "0.18.0-M2+4048-2c21e66d-SNAPSHOT"

libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-core" % "2.0.0",
    "org.http4s" %% "http4s-dsl" % http4sVersion,
    "org.http4s" %% "http4s-blaze-server" % http4sVersion,
    "org.http4s" %% "http4s-blaze-client" % http4sVersion,
    "com.typesafe.slick" %% "slick" % "3.3.1",
    "org.slf4j" % "slf4j-nop" % "1.7.26",
    "com.github.tminglei" %% "slick-pg" % "0.19.0",
    "com.github.tminglei" %% "slick-pg_circe-json" % "0.19.0",
)
