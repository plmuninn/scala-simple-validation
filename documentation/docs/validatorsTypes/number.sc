import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration

import validatorsPage.*
import validatorsPage.Validator

//case class Validator(name:String, compositionUsage:Span, implicitUsage:Span, description:String)

def markdown(using Configuration) = pageMarkdown("Number", List(
  ("Minimal value", partial.code("minimalNumberValue(8)"), partial.code(".min(8)"), "Fails if value is greater or equal minimal value", List("minimal_value")),
  ("Maximal value", partial.code("maximalNumberValue(8)"), partial.code(".max(8)"), "Fails if value is lower or equal maximal value", List("maximal_value")),
  ("Equal value", partial.code("numberEqual(8)"), partial.code(".equal(8)"), "Fail if value is not exactly same value defined in function", List("equal_field")),
))