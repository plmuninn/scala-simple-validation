import pl.muninn.markdown.Markdown.{*, given}

private val errorCodesWithDescription = List (
  ("equal_field", "Value was not equal expected value", List("any")),
  ("empty_field" , "Value was expected to be not empty",List("string", "option", "collection")),
  ("empty_expected", "Value was expected to be empty",List("string", "option", "collection")),
  ("email_field" , "Value was expected to be email",List("string")),
  ("min_count_symbols" , "Value was expected contains count of symbols",List("string")),
  ("min_count_digits" , "Value was expected contains count of digits",List("string")),
  ("min_count_lower_case" , "Value was contains count of lower case characters",List("string")),
  ("min_count_upper_case" , "Value was expected count of upper case characters",List("string")),
  ("fields_not_equal" , "Two fields were not equal",List("tuple")),
  ("minimal_value" , "Value was expected to greater or equal minimal value",List("number")),
  ("maximal_value" , "Value was expected to lower or equal maximal value",List("number")),
  ("minimal_length" , "Value was expected to have length greater or equal minimal value",List("string", "collection")),
  ("maximal_length" , "Value was expected to have length lower or equal maximal value",List("string", "collection")),
  ("expected_length" , "Value was expected to exact length",List("string", "collection")),
  ("key_missing" , "Value was expected to have specific key",List("map")),
  ("keys_missing" , "Value was expected to have specific list of keys",List("map")),
)

def markdown(using MarkdownConfig) = md {
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
         li(code("field") + m" - name of validated field")
         li(code("reason")+ m" - descriptive reason of failure")
         li(code("code") +m" - code of error")
         li(code("metadata") + m" - list of meta information of error ex. when number was expected to be lower then 10 you will find there what was expected value")
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
            col(b(m"Data types"))
          }

          for ((errorCode, description, dataTypes) <- errorCodesWithDescription) {
            row {
              col(b(errorCode))
              col(text(description))
              col(div(
                dataTypes.foreach(code(_))
              ))
            }
          }
        }
      }
    }
}