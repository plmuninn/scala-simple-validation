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

lazy val catsVersion  = "2.8.0"
lazy val munitVersion = "1.0.0-M6"

lazy val generateDocumentation = taskKey[Unit]("Generate documentation")

lazy val root = project
  .settings(releaseProcessSettings: _*)
  .settings(publishSettings: _*)
  .settings(name := "scala-simple-validation")
  .in(file("."))
  .settings(
    publishArtifact := false,
    generateDocumentation := {
      Seq("sh", ((ThisBuild / baseDirectory).value / "scripts" / "generate-docs.sh").toPath.toString) !
    }
  )
  .aggregate(foo.js, foo.jvm, foo.native)

lazy val foo =
  crossProject(JSPlatform, JVMPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("source"))
    .settings(publishSettings: _*)
    .settings(name := "scala-simple-validation")
    .settings(
      testFrameworks += new TestFramework("munit.Framework"),
      libraryDependencies ++= Seq(
        "org.scala-lang"  % "scala-reflect" % scalaVersion.value,
        "org.typelevel" %%% "cats-core"     % catsVersion,
        "org.scalameta" %%% "munit"         % munitVersion % Test
      )
    )
    .jvmConfigure(_.enablePlugins(MicrositesPlugin).settings(documentationSettings: _*))

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
  micrositeGithubRepo       := repo,
  micrositeHighlightTheme   := "atom-one-light",
  micrositePushSiteWith     := GHPagesPlugin,
  micrositeDocumentationUrl := "docs",
  mdocIn                    := file("./docs")
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
  updateOptions             := updateOptions.value.withGigahorse(false)
)

val releaseProcessSettings = Seq(
  releaseCrossBuild := true, // true if you cross-build the project for multiple Scala versions
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    releaseStepCommand("generateDocumentation"),
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    releaseStepCommandAndRemaining("+publishSigned"),
    releaseStepCommand("sonatypeBundleRelease"),
    releaseStepCommand("publishMicrosite"),
    setNextVersion,
    commitNextVersion,
    pushChanges
  )
)
