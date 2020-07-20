val http4sVersion = "0.21.6"
val circeVersion = "0.12.3"
val slickPgVersion = "0.19.0"

name := "Hospital MS"
version := "1.0"
scalaVersion := "2.13.0"

resolvers := Seq(
    Resolver.sonatypeRepo("releases")
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies := Seq(
    "org.typelevel" %% "cats-core" % "2.0.0",
    "io.circe" %% "circe-core" % circeVersion,
    "io.circe" %% "circe-generic" % circeVersion,
    "org.http4s" %% "http4s-dsl" % http4sVersion,
    "org.http4s" %% "http4s-blaze-server" % http4sVersion,
    "org.http4s" %% "http4s-blaze-client" % http4sVersion,
    "org.http4s" %% "http4s-circe" % http4sVersion,
    "com.typesafe.slick" %% "slick" % "3.3.2",
    "com.typesafe" % "config" % "1.4.0",
    "com.zaxxer" % "HikariCP" % "3.4.5",
    "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
    "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
    "com.devskiller" % "jfairy" % "0.6.0"
)

scalacOptions := Seq(
    "-Ywarn-unused"
)
