---
layout: docs
title: "Schema definitions"
permalink: docs/usage/schema/
---
# Schema definitions

You can create validation schema using composition or using implicits
```scala mdoc

 import pl.muninn.simple.validation.all._

 case class Field(name:String, otherField:String)

 val compositionSchema:Schema[Field] = createSchema { context =>
   context.field(_.name).is(noneEmptyString and stringMinimalLength(8)) +
     context.field(_.otherField).is(noneEmptyString)
 }

 compositionSchema.validate(Field("",""))

 val implicitSchema:Schema[Field] = createSchema { context =>
   (context.field(_.name).noneEmptyString and stringMinimalLength(8)) +
     context.field(_.otherField).noneEmptyString
 }

 implicitSchema.validate(Field("",""))


```
