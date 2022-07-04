import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration

import validatorsPage.*
import validatorsPage.Validator

def markdown(using Configuration) = pageMarkdown("String", List(
  ("Empty string", partial.code("emptyString"), partial.code(".emailString"), "Fails if string is not empty"),
  ("None empty string", partial.code("noneEmptyString"), partial.code(".noneEmptyString"), "Fails if string is empty"),
  ("Complex password", partial.code("complexPassword"), partial.code(".complexPassword"), "Fails if string is not at least 8 characters long and contains 1 number and 1 special symbol and big and small letters"),
  ("Minimal length", partial.code("stringMinimalLength(8)"), partial.code(".minimalLength(8)"), "Fails if length is greater or equal minimal value"),
  ("Maximal length", partial.code("stringMaximalLength(8)"), partial.code(".maximalLength(8)"), "Fails if length is lower or equal maximal value"),
  ("Expected length", partial.code("stringLength(8)"), partial.code(".expectedLength(8)"), "Fail if length is not exactly same value defined in function"),
))