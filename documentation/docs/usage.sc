import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration

def usageExample(using Configuration) = md {
  h1("Usage example")
  p{
    m"Simple example of how to use library"
  }
  p{
    codeBlock(
      "scala mdoc",
      """
        | import pl.muninn.simple.validation.all._
        |
        | case class LoginRequest(login:String, password:String)
        |
        | val schema:Schema[LoginRequest] = createSchema { context =>
        |   context.field(_.login).noneEmptyString +
        |     context.field(_.password).noneEmptyString
        | }
        |
        | val result = schema.validate(LoginRequest("admin", "admin"))
        |
        | result.isValid
        |""".stripMargin
    )
  }
}

def markdown(using Configuration) = md {
  add(usageExample)
  br
  h1(m"Library provides")
  p{
    m"Library allows to use two different ways of creating validation schema:"
    br
    ol {
      li(a("by composition of functions", "composition/"))
      li(a("by implicits of data types", "implicits/"))
    }
    br(true)
    m"It also provides simple way of"
    br
    ol {
      li(a("defining custom validators and complex validation scenarios", "custom/"))
      li(a("change field names", "field-names/"))
      li(a("compose validators", "compose-validators/"))
    }
  }
}