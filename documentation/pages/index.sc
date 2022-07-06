import pl.muninn.markdown.Markdown.{*, given}
import docs.install.installPart

def markdown(using MarkdownConfig) =
  md {
    add(docs.about.shortAbout)  
    br
    h1("Getting started")
      p {
        m"Add to yours " + code("build.sbt") + m":"
        br
        add(installPart())
        br
        m"Then you need to only add in your code:"
        br
        codeBlock("scala mdoc", "import pl.muninn.simple.validation.all._")
        br
        m"And you can start using it"
      }
      br
      add(docs.usage.usageExample)
  }
