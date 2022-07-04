import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration

import validatorsPage.*
import validatorsPage.Validator

def markdown(using Configuration) = pageMarkdown("String", List(
  ("Empty string", partial.code("emptyString"), partial.code(".emptyString"), "Fails if string is not empty", List("empty_expected")),
  ("None empty string", partial.code("noneEmptyString"), partial.code(".noneEmptyString"), "Fails if string is empty", List("empty_field")),
  ("None empty string", partial.code("emailString"), partial.code(".emailString"), "Fails if string is not email", List("email_field")),
  ("Complex password", partial.code("complexPassword"), partial.code(".complexPassword"), "Fails if string is not at least 8 characters long and contains 1 number and 1 special symbol and big and small letters", List("password_complexity")),
  ("Minimal length", partial.code("stringMinimalLength(8)"), partial.code(".minimalLength(8)"), "Fails if length is greater or equal minimal value", List("minimal_length")),
  ("Maximal length", partial.code("stringMaximalLength(8)"), partial.code(".maximalLength(8)"), "Fails if length is lower or equal maximal value", List("maximal_length")),
  ("Expected length", partial.code("stringLength(8)"), partial.code(".expectedLength(8)"), "Fail if length is not exactly same value defined in function", List("expected_length")),
))