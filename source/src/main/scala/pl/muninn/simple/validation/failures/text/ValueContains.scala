package pl.muninn.simple.validation.failures.text

import pl.muninn.simple.validation.model.InvalidField

case class ValueContains[K](field: String, expected: K) extends InvalidField {
  val reason: String = s"Value should contain $expected"

  val code: String = "value_contains"

  override val metadata: Map[String, String] = Map(
    "expected" -> expected.toString
  )
}
