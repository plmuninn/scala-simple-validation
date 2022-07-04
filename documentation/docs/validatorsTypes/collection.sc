import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration

import validatorsPage.*
import validatorsPage.Validator


def markdown(using Configuration) = pageMarkdown("Collection", List(
  ("Runs on all elements", partial.code("all(<validators>)"), partial.code(".all(<validators>)"), "Runs validators on all collection elements and fails if any of them fails"),
  ("Is not empty", partial.code("noneEmptyCollection"), partial.code(".nonEmpty"), "Fails if collection is empty"),
  ("Is empty", partial.code("emptyCollection"), partial.code(".empty"), "Fails if collection is not empty"),
  ("Minimal value", partial.code("collectionMinimalLength(8)"), partial.code(".maximumLength(8)"), "Fails if length of collection is greater or equal minimal value"),
  ("Maximal value", partial.code("collectionMaximalLength(8)"), partial.code(".minimalLength(8)"), "Fails if length of collection is lower or equal maximal value"),
  ("Exact length", partial.code("collectionLength(8)"), partial.code(".expectedLength(8)"), "Fail if length of collection is not exactly same as defined in function"),
))