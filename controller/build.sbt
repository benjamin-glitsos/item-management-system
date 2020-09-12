val doobieVersion = "0.9.0"
val http4sVersion = "0.21.6"
val circeVersion = "0.12.3"

name := "Hospital MS"
version := "1.0"
scalaVersion := "2.13.0"

resolvers := Seq(
    Resolver.sonatypeRepo("releases")
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies := Seq(
    "org.tpolecat" %% "doobie-core" % doobieVersion,
    "org.tpolecat" %% "doobie-hikari" % doobieVersion,
    "org.tpolecat" %% "doobie-postgres" % doobieVersion,
    "org.tpolecat" %% "doobie-quill" % doobieVersion,
    "org.http4s" %% "http4s-dsl" % http4sVersion,
    "org.http4s" %% "http4s-blaze-server" % http4sVersion,
    "org.http4s" %% "http4s-blaze-client" % http4sVersion,
    "org.http4s" %% "http4s-circe" % http4sVersion,
    "io.circe" %% "circe-core" % circeVersion,
    "io.circe" %% "circe-generic" % circeVersion,
    "com.devskiller" % "jfairy" % "0.6.0"
)

scalacOptions := Seq(
    // "-Ywarn-unused:imports"

    // "-Wdead-code",
    // "-Wnumeric-widen",
    // "-Woctal-literal",
    // "-Wself-implicit",
    // "-Ywarn-unused:imports,patvars,privates,locals,explicits,implicits,params,linted",
    // "-Wvalue-discard",
    // "-Xlint:adapted-args,nullary-unit,inaccessible,nullary-override,infer-any,missing-interpolator,doc-detached,private-shadow,type-parameter-shadow,poly-implicit-overload,option-implicit,delayedinit-select,package-object-classes,stars-align,constant,unused,nonlocal-return,implicit-not-found,serial,valpattern,eta-zero,eta-sam,deprecation"
)
