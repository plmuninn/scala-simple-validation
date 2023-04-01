package pl.muninn.simple.validation.failures

import pl.muninn.simple.validation.model.InvalidField

case class KeysMissing[K](field: String, expected: Iterable[K]) extends InvalidField {
  val reason: String = s"Keys ${expected.mkString(", ")} are missing"

  val code: String = "keys_missing"

  override val metadata: Map[String, String] = Map(
    "expected" -> expected.mkString(";")
  )
}
