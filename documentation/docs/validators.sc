import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.MarkdownNode.Span
import pl.muninn.markdown.common.basic.block.Table.Row
import pl.muninn.markdown.common.basic.block.Table.TableElement

import scala.collection.immutable.ListMap

val validators = ListMap(
  "Any type values" -> "any",
  "Optional values" -> "option",
  "Strings" -> "string",
  "Numbers" -> "number",
  "Collections" -> "collection",
  "Maps" -> "map",
)

def markdown(using Configuration) =
  md {
    h1("Validators")
    p {
      m"Library provides set of build in validators for types:"
      br
      p{
        ol {
          val listElements =
            for ((name, link) <- validators) yield {
            li(a(name, s"$link/"))
          }

          listElements.head
        }
      }
      br
    }
  }