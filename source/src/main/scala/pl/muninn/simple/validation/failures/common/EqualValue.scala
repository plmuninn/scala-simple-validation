package pl.muninn.simple.validation.failures.common

import pl.muninn.simple.validation.model.InvalidField

case class EqualValue[T](field: String, expected: T, value: T) extends InvalidField {
  val reason: String = s"Value must equal $expected. Got $value"

  val code: String = "equal_field"

  override val metadata: Map[String, String] = Map(
    "expected" -> expected.toString,
    "value"    -> value.toString
  )
}
