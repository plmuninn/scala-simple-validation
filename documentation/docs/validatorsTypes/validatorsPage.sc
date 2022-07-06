import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.basic.span.Code

type Validator = (String, Code, Code, String, List[String])

private def mapValidatorToRow(validator:Validator) =
  val (name, compositionUsage, implicitUsage, description, errorCodes) = validator
  partial.row {
    col(text(name))
    col(add(compositionUsage))
    col(add(implicitUsage))
    col(text(description))
    col(errorCodes.foreach(ec => code(ec)))
  }


def pageMarkdown(forType:String, validators:List[Validator])(using MarkdownConfig) =
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
          col(b(m"Error codes"))
        }
        for (validator <- validators) {
          add(mapValidatorToRow(validator))
        }
      }
    }
  }