name := "Hospital MS"
version := "1.0"
scalaVersion := "2.13.0"

val http4sVersion = "0.21.6"
val circeVersion = "0.12.3"
val slickPgVersion = "0.19.0"

libraryDependencies := Seq(
    "org.typelevel" %% "cats-core" % "2.0.0",
    "io.circe" %% "circe-core" % circeVersion,
    "io.circe" %% "circe-generic" % circeVersion,
    "io.circe" %% "circe-parser" % circeVersion,
    "org.http4s" %% "http4s-dsl" % http4sVersion,
    "org.http4s" %% "http4s-blaze-server" % http4sVersion,
    "org.http4s" %% "http4s-blaze-client" % http4sVersion,
    "com.typesafe.slick" %% "slick" % "3.3.2",
    "org.slf4j" % "slf4j-nop" % "1.7.26",
    "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
    "com.devskiller" % "jfairy" % "0.6.0",
    "eu.timepit" %% "refined" % "0.9.14"
)
