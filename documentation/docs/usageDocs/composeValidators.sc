import pl.muninn.markdown.Markdown.{*, given}

def markdown(using MarkdownConfig) = md {
  h1("Compose validators")
  p {
    m"You can easily compose own validator using defined already validators. For example:"
    br
    codeBlock(
      "scala mdoc",
      """
        | import pl.muninn.simple.validation.all._
        |
        | case class Field(name:String, otherField:String)
        |
        | val myValidString = notEmptyString and minimalLengthString(8)
        |
        | val schema:Schema[Field] = createSchema { context =>
        |   context.field(_.name).is(myValidString) +
        |     context.field(_.otherField).is(myValidString)
        | }
        |
        | schema.validate(Field("",""))
        |
        |""".stripMargin
    )
  }
}