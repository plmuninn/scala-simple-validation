package pl.muninn.simple.validation.failures.common

import pl.muninn.simple.validation.model.InvalidField

case class ExpectedEmpty(field: String) extends InvalidField {
  val reason: String = "Value must be empty"
  val code: String   = "empty_expected"
}
