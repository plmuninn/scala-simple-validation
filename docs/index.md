---
layout: home
title: Quickstart
section: quickstart
position: 1
---
# About

`scala-simple-validation` was designed to allow in simple way validate case classes and otherdata structures. It provides:
1. easy way for describing validation schema
2. few common validators to use
3. simple way of creating your own, custom validators
4. designing more complex validation process - where validation depends on some specific value

# Getting started

Add to yours `build.sbt`:
```scala

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

libraryDependencies += "pl.muninn" %% "scala-simple-validation" % "@VERSION@

```
Then you need to only add in your code:
```scala mdoc
import pl.muninn.simple.validation.all._
```
And you can start using it

# Usage example

Simple example of how to use library

```scala mdoc

 import pl.muninn.simple.validation.all._

 case class LoginRequest(login:String, password:String)

 val schema:Schema[LoginRequest] = createSchema { context =>
   context.field(_.login).notEmpty +
     context.field(_.password).notEmpty
 }

 val result = schema.validate(LoginRequest("admin", "admin"))

 result.isValid

```
