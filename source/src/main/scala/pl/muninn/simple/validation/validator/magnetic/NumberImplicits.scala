package pl.muninn.simple.validation.validator.magnetic

import pl.muninn.simple.validation.model.{Field, FieldValidator}
import pl.muninn.simple.validation.validator.typed.NumberValidators

trait NumberImplicits {

  implicit class NumberValidation[T](field: Field[T])(implicit numeric: Numeric[T]) {
    def min(value: T): FieldValidator[T]   = field.is(NumberValidators.min(value))
    def max(value: T): FieldValidator[T]   = field.is(NumberValidators.max(value))
    def equal(value: T): FieldValidator[T] = field.is(NumberValidators.equalNumber(value))
  }

}

object NumberImplicits extends NumberImplicits
