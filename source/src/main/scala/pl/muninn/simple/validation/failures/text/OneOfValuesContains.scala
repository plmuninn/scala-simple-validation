package pl.muninn.simple.validation.failures.text

import pl.muninn.simple.validation.model.InvalidField

case class OneOfValuesContains[K](field: String, expected: Iterable[K]) extends InvalidField {
  val reason: String = s"One of values ${expected.mkString(", ")} should be found"

  val code: String = "one_of_values_contains"

  override val metadata: Map[String, String] = Map(
    "expected" -> expected.mkString(",")
  )
}
