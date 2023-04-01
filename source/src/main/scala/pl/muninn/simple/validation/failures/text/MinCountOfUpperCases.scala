package pl.muninn.simple.validation.failures.text

import pl.muninn.simple.validation.model.InvalidField

case class MinCountOfUpperCases(field: String, expected: Int, found: Int) extends InvalidField {
  val reason: String = s"Count of upper cases must be greater or equal $expected. Got $found"

  val code: String = "min_count_upper_case"

  override val metadata: Map[String, String] = Map(
    "expected" -> expected.toString,
    "count"    -> found.toString
  )
}
