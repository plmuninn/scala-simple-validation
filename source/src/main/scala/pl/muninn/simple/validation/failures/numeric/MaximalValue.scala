package pl.muninn.simple.validation.failures.numeric

import pl.muninn.simple.validation.model.InvalidField

case class MaximalValue[E, R](field: String, expected: E, value: R) extends InvalidField {
  val reason: String = s"Value must be lower or equal $expected. Got $value"
  val code: String   = "maximal_value"

  override val metadata: Map[String, String] = Map(
    "expected" -> expected.toString,
    "value"    -> value.toString
  )
}
