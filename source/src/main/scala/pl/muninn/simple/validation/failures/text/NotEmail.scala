package pl.muninn.simple.validation.failures.text

import pl.muninn.simple.validation.model.InvalidField

case class NotEmail(field: String) extends InvalidField {
  val reason: String = "Value must be a valid email"
  val code: String   = "email_field"
}
