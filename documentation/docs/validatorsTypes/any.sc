import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration

import validatorsPage.*
import validatorsPage.Validator


def markdown(using Configuration) = pageMarkdown("Any type", List(
  ("Values equal", partial.code("equalValue(<value>)"), partial.code(".equalValue(<value>)"), "Runs validators on all collection elements and fails if any of them fails"),
  ("Custom validator", partial.code("customValid(<error_code>, <error_reason>, <metadata>)(<function>)"), partial.code(".custom(<code>, <reason>, <metadata>)(<function>)"), "Fails if function returns false"),
  ("Fields equal", partial.code("fieldsEqual"), partial.code(".fieldsEqual"), "Fails if tuple values is not equal"),
))