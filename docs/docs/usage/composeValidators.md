---
layout: docs
title: "Compose validators"
permalink: docs/usage/compose-validators/
---
# Compose validators

You can easily compose own validator using defined already validators. For example:
```scala mdoc

 import pl.muninn.simple.validation.all._

 case class Field(name:String, otherField:String)

 val myValidString = noneEmptyString and stringMinimalLength(8)

 val schema:Schema[Field] = createSchema { context =>
   context.field(_.name).is(myValidString) +
     context.field(_.otherField).is(myValidString)
 }

 schema.validate(Field("",""))


```
