import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration
import parts.*

def markdown(using Configuration) =
  md {
    add(docs.about.shortAbout)  
    br
    h1("Getting started")
      p {
        m"Add to yours " + code("build.sbt") + m":"
        br
        add(install.markdown)
        br
        m"Then you need to only add in your code:"
        br
        codeBlock("scala mdoc", "import pl.muninn.simple.validation.all._")
        br
        m"And you can start using it"
      }
      br
      h1("Usage example")
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
            | assert(result.isValid)
            |""".stripMargin
        )
    }
  }
