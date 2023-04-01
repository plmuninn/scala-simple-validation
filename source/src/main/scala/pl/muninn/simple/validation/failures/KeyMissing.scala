package pl.muninn.simple.validation.failures

import pl.muninn.simple.validation.model.InvalidField

case class KeyMissing[K](field: String, expected: K) extends InvalidField {
  val reason: String = s"Key $expected is missing"

  val code: String = "key_missing"

  override val metadata: Map[String, String] = Map(
    "expected" -> expected.toString
  )
}
