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
    "org.http4s" %% "http4s-dsl",
    "org.http4s" %% "http4s-blaze-server",
    "org.http4s" %% "http4s-blaze-client",
    "org.http4s" %% "http4s-circe"
).map(_ % "0.21.6")

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser",
).map(_ % "0.12.3")

libraryDependencies ++= Seq(
    "io.circe" %% "circe-optics" % "0.12.0"
)

libraryDependencies ++= Seq(
    "com.devskiller" % "jfairy" % "0.6.0",
    "com.github.everit-org.json-schema" % "org.everit.json.schema" % "1.12.1"
)

scalacOptions := Seq(
    "-Ymacro-annotations"
)
// "-Ywarn-unused:imports"
// "-Wdead-code",
// "-Wnumeric-widen",
// "-Woctal-literal",
// "-Wself-implicit",
// "-Ywarn-unused:imports,patvars,privates,locals,explicits,implicits,params,linted",
// "-Wvalue-discard",
// "-Xlint:adapted-args,nullary-unit,inaccessible,nullary-override,infer-any,missing-interpolator,doc-detached,private-shadow,type-parameter-shadow,poly-implicit-overload,option-implicit,delayedinit-select,package-object-classes,stars-align,constant,unused,nonlocal-return,implicit-not-found,serial,valpattern,eta-zero,eta-sam,deprecation"
