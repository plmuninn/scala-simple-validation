//> using scala "3.1"

import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.basic.span.Code

type Validator = (String, Code, Code, String)

private def mapValidatorToRow(validator:Validator) =
  val (name, compositionUsage, implicitUsage, description) = validator
  partial.row {
    col(text(name))
    col(add(compositionUsage))
    col(add(implicitUsage))
    col(text(description))
  }


def pageMarkdown(forType:String, validators:List[Validator])(using Configuration) =
  md {
    h1(m"$forType Validators")
    br
    m"Validators for "+ b(text(forType)) +m" are :"
    br
    p{
      table {
        headers {
          col(b(m"Name"))
          col(b(m"Usage in composition syntax"))
          col(b(m"Usage in implicit syntax"))
          col(b(m"Description"))
        }
        validators.map(mapValidatorToRow).map(add).last
      }
    }
  }