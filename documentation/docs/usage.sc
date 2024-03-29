import pl.muninn.markdown.Markdown.{*, given}

def usageExample(using MarkdownConfig) = md {
  h1("Usage example")
  p{
    m"Simple example of how to use library"
  }
  p{
    codeBlock(
      "scala mdoc",
      """
        | import pl.muninn.simple.validation._
        |
        | case class LoginRequest(login:String, password:String)
        |
        | val schema:ValidationSchema[LoginRequest] = createSchema { context =>
        |   context.field(_.login).notEmpty +
        |     context.field(_.password).notEmpty
        | }
        |
        | val result = schema.validate(LoginRequest("admin", "admin"))
        |
        | result.isValid
        |""".stripMargin
    )
  }
}

def markdown(using MarkdownConfig) = md {
  add(usageExample)
  br
  h1(m"Library provides")
  p{
    m"Library allows to use two different ways of creating validation " + a("schema", "schema/") + m" ."
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