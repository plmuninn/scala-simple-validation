import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration

import validatorsPage.*
import validatorsPage.Validator

def markdown(using Configuration) = pageMarkdown("Map", List(
  ("Contains key", partial.code("containsKey(\"key\")"), partial.code(".containsKey(\"key\")"), "Fails if map doesn't contains key", List("key_missing")),
  ("Contains Keys", partial.code("containsKeys(List(\"key1\", \"key2\"))"), partial.code(".containsKeys(List(\"key1\", \"key2\"))"), "Fails if map doesn't contains all keys", List("key_missing", "keys_missing")),
  ("Is not empty", partial.code("notEmpty"), partial.code(".notEmpty"), "Fails if collection is empty", List("empty_field")),
  ("Is empty", partial.code("empty"), partial.code(".empty"), "Fails if collection is not empty", List("empty_expected")),
  ("Minimal value", partial.code("minimalLength(8)"), partial.code(".minimalLength(8)"), "Fails if length of collection is greater or equal minimal value", List("minimal_length")),
  ("Maximal value", partial.code("maximumLength(8)"), partial.code(".maximumLength(8)"), "Fails if length of collection is lower or equal maximal value", List("maximal_length")),
  ("Exact length", partial.code("expectedLength(8)"), partial.code(".expectedLength(8)"), "Fail if length of collection is not exactly same as defined in function", List("expected_length")),
))