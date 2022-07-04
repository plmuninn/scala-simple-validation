import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration
import scala.collection.immutable.ListMap

private val errorCodesWithDescription = ListMap (
  "equal_field" -> "Value was not equal expected value",
  "empty_field" -> "Value was expected to be not empty",
  "empty_expected" -> "Value was expected to be empty",
  "email_field" -> "Value was expected to be email",
  "password_complexity" -> "Value was expected to complex password",
  "fields_not_equal" -> "Two fields were not equal",
  "minimal_value" -> "Value was expected to greater or equal minimal value",
  "maximal_value" -> "Value was expected to lower or equal maximal value",
  "expected_value" -> "Value was expected to contains some value",
  "minimal_length" -> "Value was expected to have length greater or equal minimal value",
  "maximal_length" -> "Value was expected to have length lower or equal maximal value",
  "expected_length" -> "Value was expected to exact length",
  "key_missing" -> "Value was expected to have specific key",
  "keys_missing" -> "Value was expected to have specific list of keys",
)

def markdown(using Configuration) = md {
    h1("Errors")
    p {
        m"Error structure is reperesented by simple trait:"
        br
        codeBlock("scala",
        """trait InvalidField {
           |  def field: String
           |  def reason: String
           |  def code: String
           |  def metadata: Map[String, String] = Map.empty
           |}""".stripMargin
        )
       br
       m"Where each filed represents:"
       br
       ol {
         li {
           code("field")
           m" - name of validated field"
         }
         li {
           code("reason")
           m" - descriptive reason of failure"
         }
         li {
           code("code")
           m" - code of error"
         }
         li {
           code("metadata")
           m" - list of meta information of error ex. when number was expected to be lower then 10 you will find there what was expected value"
         }
       }
    }
    br
    h1("List of error codes")
    p{
      m"Here is build in list of errors codes with descriptions:"
      br
      p{
        table {
          headers {
            col(b(m"Code"))
            col(b(m"Description"))
          }

          val rows = for ((code, description) <- errorCodesWithDescription) yield {
            row {
              col(b(code))
              col(text(description))
            }
          }

          rows.head
        }
      }
    }
}