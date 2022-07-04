import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration

def markdown(using Configuration) = md {
  h1("Schema definitions")
  p {
    m"You can create validation schema using composition or using implicits"
    br
    codeBlock(
      "scala mdoc",
      """
        | import pl.muninn.simple.validation.all._
        |
        | case class Field(name:String, otherField:String)
        |
        | val compositionSchema:Schema[Field] = createSchema { context =>
        |   context.field(_.name).is(noneEmptyString and stringMinimalLength(8)) +
        |     context.field(_.otherField).is(noneEmptyString)
        | }
        |
        | compositionSchema.validate(Field("",""))
        |
        | val implicitSchema:Schema[Field] = createSchema { context =>
        |   (context.field(_.name).noneEmptyString and stringMinimalLength(8)) +
        |     context.field(_.otherField).noneEmptyString
        | }
        |
        | implicitSchema.validate(Field("",""))
        |
        |""".stripMargin
    )
  }
}