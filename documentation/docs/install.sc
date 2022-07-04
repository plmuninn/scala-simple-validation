import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration

def installPart(using Configuration) =
  partial.codeBlock(
    "scala",
    """
      |resolvers ++= Seq(
      |  Resolver.sonatypeRepo("releases"),
      |  Resolver.sonatypeRepo("snapshots")
      |)
      |
      |libraryDependencies += "pl.muninn" %% "scala-simple-validation" % "@VERSION@
      |""".stripMargin
  )

def markdown(using Configuration) = 
    md {
     p{
        m"To install library add to your " + code("build.sbt") + " file:"
        br
        add(installPart)
     }
    }