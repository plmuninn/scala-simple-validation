import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration

def installPart(isScalaNative:Boolean = false)(using Configuration) =
  partial.codeBlock(
    "scala",
    s"""
      |resolvers ++= Seq(
      |  Resolver.sonatypeRepo("releases"),
      |  Resolver.sonatypeRepo("snapshots")
      |)
      |
      |libraryDependencies += "pl.muninn" ${if isScalaNative then "%%%" else "%%"} "scala-simple-validation" % "@VERSION@
      |""".stripMargin
  )

def markdown(using Configuration) = 
    md {
     p{
        m"To install library add to your " + code("build.sbt") + " file:"
        br
        add(installPart())
     }
     br
     h1("Support for ScalaJS and ScalaNative")
     m"Library is published as ScalaJS and ScalaNative libraries too - to use it just add to your " + code("build.sbt") + " file:"
     br
     add(installPart(true))
    }