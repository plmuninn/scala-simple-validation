package pl.muninn.simple.validation.failures.text

import pl.muninn.simple.validation.model.InvalidField

case class OneOfValuesMissing[K](field: String, expected: Iterable[K]) extends InvalidField {
  val reason: String = s"One of values ${expected.mkString(", ")} is missing"

  val code: String = "one_of_values_missing"

  override val metadata: Map[String, String] = Map(
    "expected" -> expected.mkString(",")
  )
}
