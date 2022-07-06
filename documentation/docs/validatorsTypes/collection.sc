import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration

import validatorsPage.*
import validatorsPage.Validator


def markdown(using Configuration) = pageMarkdown("Collection", List(
  ("Runs on all elements", partial.code("all(<validators>)"), partial.code(".all(<validators>)"), "Runs validators on all collection elements and fails if any of them fails", List("N\\A")),
  ("Is not empty", partial.code("notEmptyCollection"), partial.code(".notEmpty"), "Fails if collection is empty", List("empty_field")),
  ("Is empty", partial.code("emptyCollection"), partial.code(".empty"), "Fails if collection is not empty", List("empty_expected")),
  ("Minimal value", partial.code("minimalLengthCollection(8)"), partial.code(".minimalLength(8)"), "Fails if length of collection is greater or equal minimal value", List("minimal_length")),
  ("Maximal value", partial.code("maximalLengthCollection(8)"), partial.code(".maximumLength(8)"), "Fails if length of collection is lower or equal maximal value", List("maximal_length")),
  ("Exact length", partial.code("exactLengthCollection(8)"), partial.code(".expectedLength(8)"), "Fail if length of collection is not exactly same as defined in function", List("expected_length")),
))