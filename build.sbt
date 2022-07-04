import scala.sys.process._

import ReleaseTransformations._
import xerial.sbt.Sonatype._

val compilerOptions = Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-unchecked",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Xfatal-warnings"
)

val username = "plmuninn"
val repo     = "scala-simple-validation"

ThisBuild / scalaVersion  := "2.13.8"
ThisBuild / name          := repo
ThisBuild / organization  := "pl.muninn"
ThisBuild / scalacOptions := compilerOptions

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

lazy val catsVersion = "2.7.0"
lazy val cats        = Seq("org.typelevel" %% "cats-core" % catsVersion)

lazy val munitVersion = "0.7.29"
lazy val tests        = Seq("org.scalameta" %% "munit" % munitVersion % Test)

lazy val generateDocumentation = taskKey[Unit]("Generate documentation")

lazy val root = (project in file("."))
  .enablePlugins(MicrositesPlugin)
  .settings(publishSettings: _*)
  .settings(documentationSettings: _*)
  .settings(name := "scala-simple-validation")
  .settings(libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value)
  .settings(libraryDependencies ++= (cats ++ tests))
  .settings(
    generateDocumentation := {
      Seq("sh", ((ThisBuild / baseDirectory).value / "scripts" / "generate-docs.sh").toPath.toString) !
    }
  )

lazy val documentationSettings = Seq(
  mdocVariables := Map(
    "VERSION"      -> version.value,
    "CATS_VERSION" -> catsVersion
  ),
  micrositeName             := repo,
  micrositeDescription      := "Simple scala validation library",
  micrositeUrl              := s"https://$username.github.io",
  micrositeBaseUrl          := s"/$repo",
  micrositeHomepage         := s"https://$username.github.io/$repo/",
  micrositeAuthor           := "Maciej Romański Muninn Software",
  micrositeGithubOwner      := username,
  micrositeGithubRepo       := s"https://$username.github.io/$repo/",
  micrositeHighlightTheme   := "atom-one-light",
  micrositePushSiteWith     := GHPagesPlugin,
  micrositeDocumentationUrl := "docs"
)

lazy val publishSettings = Seq(
  publishTo              := sonatypePublishToBundle.value,
  sonatypeProjectHosting := Some(GitHubHosting(username, repo, "maciej.romanski@o2.pl")),
  homepage               := Some(url(s"https://github.com/$username/$repo")),
  licenses += "MIT"      -> url(s"https://github.com/$username/$repo/blob/master/LICENSE"),
  developers := List(
    Developer(
      id = username,
      name = "Maciej Romanski",
      email = "maciej.romanski@o2.pl",
      url = new URL(s"http://github.com/$username")
    )
  ),
  scmInfo                := Some(ScmInfo(url(s"https://github.com/$username/$repo"), s"git@github.com:$username/$repo.git")),
  apiURL                 := Some(url(s"https://$username.github.io/$repo/latest/api/")),
  publishTo              := sonatypePublishToBundle.value,
  publishMavenStyle      := true,
  Test / publishArtifact := false,
  //   Following 2 lines need to get around https://github.com/sbt/sbt/issues/4275
  publishConfiguration      := publishConfiguration.value.withOverwrite(true),
  publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true),
  updateOptions             := updateOptions.value.withGigahorse(false),
  releaseCrossBuild         := false, // true if you cross-build the project for multiple Scala versions
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    // For non cross-build projects, use releaseStepCommand("publishSigned")
    releaseStepCommandAndRemaining("publishSigned"),
    releaseStepCommand("sonatypeBundleRelease"),
    releaseStepCommand("generateDocumentation"),
    releaseStepCommand("publishMicrosite"),
    setNextVersion,
    commitNextVersion,
    pushChanges
  )
)
