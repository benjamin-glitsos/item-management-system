name := System.getenv("PROJECT_NAME")
version := "1.0"
scalaVersion := "2.13.0"

val doobieVersion = "0.9.0"
val jfairyVersion = "0.6.0"
val jsonSchemaVersion = "1.12.1"
val jansiVersion = "2.1.1"
val uPickleVersion = "0.9.5"
val caseInsensitiveVersion = "0.3.0"
val akkaVersion = "2.6.8"
val akkaHttpVersion = "10.2.2"
val jedisVersion = "3.6.0"

Global / onChangedBuildSource := ReloadOnSourceChanges

resolvers := Seq(
    "jitpack" at "https://jitpack.io"
)

libraryDependencies ++= Seq(
    "org.tpolecat" %% "doobie-core",
    "org.tpolecat" %% "doobie-hikari",
    "org.tpolecat" %% "doobie-postgres",
    "org.tpolecat" %% "doobie-quill"
).map(_ % doobieVersion)

libraryDependencies ++= Seq(
    "com.devskiller" % "jfairy" % jfairyVersion,
    "com.github.everit-org.json-schema" % "org.everit.json.schema" % jsonSchemaVersion,
    "org.fusesource.jansi" % "jansi" % jansiVersion,
    "com.lihaoyi" %% "upickle" % uPickleVersion,
    "org.typelevel" %% "case-insensitive" % caseInsensitiveVersion,
    "redis.clients" % "jedis" % jedisVersion
)

libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
)

scalacOptions := Seq(
    "-Ymacro-annotations",
    "-Ywarn-unused:imports"
)
