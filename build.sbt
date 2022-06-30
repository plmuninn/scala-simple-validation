ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val catsVersion = "2.7.0"
lazy val cats        = Seq("org.typelevel" %% "cats-core" % catsVersion)

lazy val munitVersion = "0.7.29"
lazy val tests        = Seq("org.scalameta" %% "munit" % munitVersion % Test)

lazy val root = (project in file("."))
  .settings(name := "scala-simple-validation")
  .settings(libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value)
  .settings(libraryDependencies ++= (cats ++ tests))
