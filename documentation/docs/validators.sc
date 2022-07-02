import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.MarkdownNode.Span
import pl.muninn.markdown.common.basic.block.Table.Row
import pl.muninn.markdown.common.basic.block.Table.TableElement

case class Validator(name:String, forType:Span, compositionUsage:Span, implicitUsage:Span)

val validators = List(
  Validator("", partial.text(""), partial.text(""), partial.text(""))
)

def mapValidatorToRow(validator:Validator):TableElement =
  partial.row {
    col(text(validator.name))
    col(add(validator.forType))
    col(add(validator.compositionUsage))
    col(add(validator.implicitUsage))
  }

def markdown(using Configuration) =
  md {
    h1("Validators")
    p {
      m"Validators provided by library:"
      br
      table {
        headers {
          col("Name")
          col("For what type")
          col("Composition usage")
          col("Implicit usage")
        }
        validators.map(mapValidatorToRow).map(add).last
      }
    }
  }