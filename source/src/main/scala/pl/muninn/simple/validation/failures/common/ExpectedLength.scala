package pl.muninn.simple.validation.failures.common

import pl.muninn.simple.validation.model.InvalidField

case class ExpectedLength[E, R](field: String, expected: E, value: R) extends InvalidField {
  val reason: String = s"Length must be equal $expected. Got $value"
  val code: String   = "expected_length"

  override val metadata: Map[String, String] = Map(
    "expected" -> expected.toString,
    "value"    -> value.toString
  )
}
