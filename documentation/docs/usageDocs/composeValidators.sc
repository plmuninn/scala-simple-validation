import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration

def markdown(using Configuration) = md {
  h1("Compose validators")
  p {
    m"You can easily define own validator for specific task. For example:"
    br
    codeBlock(
      "scala mdoc",
      """
        | import pl.muninn.simple.validation.all._
        |
        | case class Field(name:String, otherField:String)
        |
        | val myValidString = noneEmptyString and stringMinimalLength(8)
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