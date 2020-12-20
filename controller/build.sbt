name := "Inventory Management System"
version := "1.0"
scalaVersion := "2.13.0"

Global / onChangedBuildSource := ReloadOnSourceChanges

resolvers := Seq(
    "jitpack" at "https://jitpack.io"
)

libraryDependencies ++= Seq(
    "org.tpolecat" %% "doobie-core",
    "org.tpolecat" %% "doobie-hikari",
    "org.tpolecat" %% "doobie-postgres",
    "org.tpolecat" %% "doobie-quill"
).map(_ % "0.9.0")

libraryDependencies ++= Seq(
    "com.devskiller" % "jfairy" % "0.6.0",
    "com.github.everit-org.json-schema" % "org.everit.json.schema" % "1.12.1",
    "org.fusesource.jansi" % "jansi" % "2.1.1",
    "com.lihaoyi" %% "upickle" % "0.9.5"
)

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.2"

libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
    "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
    "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion
)

scalacOptions := Seq(
    "-Ymacro-annotations",
    "-Ywarn-unused:imports"
)

// 
// "-Wdead-code",
// "-Wnumeric-widen",
// "-Woctal-literal",
// "-Wself-implicit",
// "-Ywarn-unused:imports,patvars,privates,locals,explicits,implicits,params,linted",
// "-Wvalue-discard",
// "-Xlint:adapted-args,nullary-unit,inaccessible,nullary-override,infer-any,missing-interpolator,doc-detached,private-shadow,type-parameter-shadow,poly-implicit-overload,option-implicit,delayedinit-select,package-object-classes,stars-align,constant,unused,nonlocal-return,implicit-not-found,serial,valpattern,eta-zero,eta-sam,deprecation"
