import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration

def markdown(using Configuration) =
  md {
    h1("About")
      p{
        code("scala-simple-validation") + m" was designed to allow in simple way validate case classes and other data"
        m"data structures. It provides:"
        br
        ul{
          li(m"easy way for describing validation schema")
          li(m"few common validators to use")
          li(m"simple way of creating your own, custom validators")
          li(m"designing more complex validation process - where validation depends on some specific value")
        }
      }
    br
    h1("Getting started")
      p {
        m"Add to yours " + code("build.sbt") + m":"
        br
        codeBlock(
          "sbtshell",
          """
            |resolvers ++= Seq(
            |  Resolver.sonatypeRepo("releases"),
            |  Resolver.sonatypeRepo("snapshots")
            |)
            |
            |libraryDependencies += "pl.muninn" %% "scala-simple-validation" % "@VERSION@
            |""".stripMargin
        )
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
    h1("Dependencies")
    p{
      m"Libraray is build using " 
      a("cats", "https://typelevel.org/cats/" , "cats")
      m" in version " 
      b(m"@CATS_VERSION@")
    }
  }
