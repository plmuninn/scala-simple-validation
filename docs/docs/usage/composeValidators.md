---
layout: docs
title: "Compose validators"
permalink: docs/usage/compose-validators/
---
# Compose validators

You can easily compose own validator using defined already validators. For example:
```scala mdoc

 import pl.muninn.simple.validation._

 case class Field(name:String, otherField:String)

 val myValidString = notEmptyString and minimalLengthString(8)

 val schema:ValidationSchema[Field] = createSchema { context =>
   context.field(_.name).is(myValidString) +
     context.field(_.otherField).is(myValidString)
 }

 schema.validate(Field("",""))


```

# Use schema as validators

You can easily use existing schema as validator for field. For example:
```scala mdoc

 import pl.muninn.simple.validation._

 case class InputValue(name:String)

 val inputSchema:ValidationSchema[InputValue] = createSchema { context =>
   context.field(_.name).notEmpty
 }

 case class RequestClass(input:InputValue)

 val requestSchema:ValidationSchema[RequestClass] = createSchema { context =>
   context.field(_.input).is(inputSchema)
 }

 requestSchema.validate(RequestClass(InputValue("")))


```
