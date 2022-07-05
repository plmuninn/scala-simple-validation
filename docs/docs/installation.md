---
layout: docs
title: "Install"
permalink: docs/install/
---

To install library add to your `build.sbt` file:
```scala

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

libraryDependencies += "pl.muninn" %% "scala-simple-validation" % "@VERSION@

```

# Support for ScalaJS and ScalaNative
Library is published as ScalaJS and ScalaNative libraries too - to use it just add to your `build.sbt` file:
```scala

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

libraryDependencies += "pl.muninn" %%% "scala-simple-validation" % "@VERSION@

```