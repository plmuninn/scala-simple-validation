import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration

import validatorsPage.*
import validatorsPage.Validator

def markdown(using Configuration) = pageMarkdown("String", List(
  ("Empty string", partial.code("emptyString"), partial.code(".empty"), "Fails if string is not empty", List("empty_expected")),
  ("None empty string", partial.code("notEmpty"), partial.code(".notEmptyString"), "Fails if string is empty", List("empty_field")),
  ("Email", partial.code("email"), partial.code(".email"), "Fails if string is not email", List("email_field")),
  ("Password", partial.code("password()"), partial.code(".password"), "Fails if string is not at least 8 characters long and not contains 1 number and 1 special symbol and big and small letters", List("minimal_length", "min_count_symbols", "min_count_digits", "min_count_lower_case", "min_count_upper_case")),
  ("Minimal length", partial.code("minimalLengthString(8)"), partial.code(".minimalLength(8)"), "Fails if length is greater or equal minimal value", List("minimal_length")),
  ("Maximal length", partial.code("maximalLengthString(8)"), partial.code(".maximalLength(8)"), "Fails if length is lower or equal maximal value", List("maximal_length")),
  ("Expected length", partial.code("exactLengthString(8)"), partial.code(".exactLength(8)"), "Fail if length is not exactly same value defined in function", List("expected_length")),
  ("Contains symbols", partial.code("minimalCountSymbols(1, <list of symbols>)"), partial.code(".containsSymbols(1, <list of symbols>)"), "Fail if count of symbols is lower of minimal value", List("min_count_symbols")),
  ("Contains digits", partial.code("minimalCountDigits(1)"), partial.code(".containsDigits(1)"), "Fail if count of digits is lower of minimal value", List("min_count_digits")),
  ("Contains lower case characters", partial.code("minimalCountLowerCases(1)"), partial.code(".containsLowerCase(1)"), "Fail if count of lower case characters is lower of minimal value", List("min_count_lower_case")),
  ("Contains upper case characters", partial.code("minimalCountUpperCases(1)"), partial.code(".containsUpperCase(1)"), "Fail if count of upper case characters is lower of minimal value", List("min_count_upper_case")),
))