package pl.muninn.simple.validation.failures.common

import pl.muninn.simple.validation.model.InvalidField

case class EmptyField(field: String) extends InvalidField {
  val reason: String = "Non empty value required"
  val code: String   = "empty_field"
}
