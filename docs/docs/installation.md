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
