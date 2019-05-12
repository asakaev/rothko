ThisBuild / version := "0.0.1"
ThisBuild / scalaVersion := "2.12.8"
ThisBuild / organization := "io.github.asakaev"

lazy val rothko = (project in file("."))
  .settings(
    name := "rothko",
    libraryDependencies ++= List(
      "org.typelevel" %% "cats-core" % "1.6.0",
      "co.fs2"        %% "fs2-core"  % "1.0.4"
    ),
    libraryDependencies ++= List(
      "org.scalatest"  %% "scalatest"  % "3.0.5",
      "org.scalacheck" %% "scalacheck" % "1.14.0"
    ).map(_ % Test),
    scalacOptions ++= List(
      "-deprecation",
      "-Xlint",
      "-Xfatal-warnings",
      "-language:higherKinds",
      "-Ypartial-unification"
    )
  )
