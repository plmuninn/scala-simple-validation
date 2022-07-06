import pl.muninn.markdown.Markdown.{*, given}

def markdown(using MarkdownConfig) = md {
  h1("Field names")
  p {
    m"Field name can be set or retrieved using macro:"
    br
    codeBlock(
      "scala mdoc",
      """
        | import pl.muninn.simple.validation.all._
        |
        | case class Field(name:String, otherField:String)
        |
        | val macroSchema:Schema[Field] = createSchema { context =>
        |   context.field(_.name).notEmpty +
        |     context.field(_.otherField).notEmpty
        | }
        |
        | macroSchema.validate(Field("",""))
        |
        | val customNameSchema:Schema[Field] = createSchema { context =>
        |   context.field("name")(_.name).notEmpty +
        |     context.field("myName")(_.otherField).notEmpty
        | }
        |
        | customNameSchema.validate(Field("",""))
        |
        |""".stripMargin
    )
  }
  br
  h1("Macro names")
  p {
    m"Macro design for retrieving value name was done in a way to allow user get complex name in easy way. For example:"
    br
    codeBlock(
      "scala mdoc",
      """
        | import pl.muninn.simple.validation.all._
        |
        | case class ComplexOtherField(otherField:String)
        | case class ComplexField(field:Option[ComplexOtherField])
        |
        | val schema:Schema[ComplexField] = createSchema { context =>
        |   // field name will be `field.otherField`
        |   context.field(_.field.map(_.otherField)).definedAnd(notEmptyString)
        | }
        |
        | schema.validate(ComplexField(None))
        | schema.validate(ComplexField(Some(ComplexOtherField("value"))))
        |
        |""".stripMargin
    )
  }
}