import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration

def markdown(using Configuration) = 
    md {
     p{
        m"To install library add to your " + code("build.sbt") + " file:"
        br
        add(parts.install.markdown)
     }
    }