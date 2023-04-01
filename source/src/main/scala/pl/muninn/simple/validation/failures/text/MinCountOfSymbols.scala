package pl.muninn.simple.validation.failures.text

import pl.muninn.simple.validation.model.InvalidField

case class MinCountOfSymbols(field: String, expected: Int, found: Int, symbols: List[Char]) extends InvalidField {
  val reason: String = s"Count of symbols must be greater or equal $expected. Got $found"

  val code: String = "min_count_symbols"

  override val metadata: Map[String, String] = Map(
    "expected"         -> expected.toString,
    "count"            -> found.toString,
    "expected_symbols" -> symbols.map(symbol => s"'$symbol'").mkString(";")
  )
}
