---
layout: docs
title: "Usage"
permalink: docs/usage/
---
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

# Library provides

Library allows to use two different ways of creating validation [schema](schema/) .  
It also provides simple way of
* [defining custom validators and complex validation scenarios](custom/)
* [change field names](field-names/)
* [compose validators](compose-validators/)
