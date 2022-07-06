import pl.muninn.markdown.Markdown.{*, given}

import scala.collection.immutable.ListMap

val validators = ListMap(
  "Any type values" -> "any",
  "Optional values" -> "option",
  "Strings" -> "string",
  "Numbers" -> "number",
  "Collections" -> "collection",
  "Maps" -> "map",
)

def markdown(using MarkdownConfig) =
  md {
    h1("Validators")
    p {
      m"Library provides set of build in validators for types:"
      br
      p{
        ol {
            for ((name, link) <- validators)  {
              li(a(name, s"$link/"))
          }
        }
      }
      br
    }
  }