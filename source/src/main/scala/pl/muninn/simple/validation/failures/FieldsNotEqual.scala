package pl.muninn.simple.validation.failures

import pl.muninn.simple.validation.model.InvalidField

case class FieldsNotEqual(field: String) extends InvalidField {
  val reason: String = s"Values $field are not equal"
  val code: String   = "fields_not_equal"
}
