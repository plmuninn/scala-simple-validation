import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration

import validatorsPage.*
import validatorsPage.Validator

def markdown(using Configuration) = pageMarkdown("Map", List(
  ("Contains key", partial.code("containsKey(\"key\")"), partial.code(".containsKey(\"key\")"), "Fails if map doesn't contains key", List("key_missing")),
  ("Contains Keys", partial.code("containsKeys(List(\"key1\", \"key2\"))"), partial.code(".containsKeys(List(\"key1\", \"key2\"))"), "Fails if map doesn't contains all keys", List("key_missing", "keys_missing")),
))