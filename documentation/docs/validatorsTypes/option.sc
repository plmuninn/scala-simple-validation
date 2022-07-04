import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration

import validatorsPage.*
import validatorsPage.Validator

def markdown(using Configuration) = pageMarkdown("Option", List(
  ("When value is defined", partial.code("ifDefined(<validators>)"), partial.code(".ifDefined(<validators>)"), "Runs passed to it validators when value is defined", List("N\\A")),
  ("Is defined and", partial.code("N\\A"), partial.code("definedAnd(<validators>)"), "Fails if value is not defined and runs passed to it validators if value is defined", List("empty_field")),
  ("If is defined", partial.code("isDefined"), partial.code(".isDefined"), "Fails if value is not defined", List("empty_field")),
  ("If is not defined", partial.code("notDefined"), partial.code(".notDefined"), "Fails if value is defined", List("empty_expected")),
))