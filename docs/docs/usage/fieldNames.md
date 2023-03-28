---
layout: docs
title: "Field names"
permalink: docs/usage/field-names/
---
# Field names

Field name can be set or retrieved using macro:
```scala mdoc

 import pl.muninn.simple.validation._

 case class Field(name:String, otherField:String)

 val macroSchema:ValidationSchema[Field] = createSchema { context =>
   context.field(_.name).notEmpty +
     context.field(_.otherField).notEmpty
 }

 macroSchema.validate(Field("",""))

 val customNameSchema:ValidationSchema[Field] = createSchema { context =>
   context.field("name")(_.name).notEmpty +
     context.field("myName")(_.otherField).notEmpty
 }

 customNameSchema.validate(Field("",""))


```

# Macro names

Macro design for retrieving value name was done in a way to allow user get complex name in easy way. For example:
```scala mdoc

 import pl.muninn.simple.validation._

 case class ComplexOtherField(otherField:String)
 case class ComplexField(field:Option[ComplexOtherField])

 val schema:ValidationSchema[ComplexField] = createSchema { context =>
   // field name will be `field.otherField`
   context.field(_.field.map(_.otherField)).definedAnd(notEmptyString)
 }

 schema.validate(ComplexField(None))
 schema.validate(ComplexField(Some(ComplexOtherField("value"))))


```
