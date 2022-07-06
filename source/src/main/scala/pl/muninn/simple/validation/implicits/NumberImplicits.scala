package pl.muninn.simple.validation.implicits

import pl.muninn.simple.validation.model.{Validation, ValidationWithValidators}
import pl.muninn.simple.validation.validators.NumberValidators

trait NumberImplicits {

  implicit class NumberValidation[T](field: Validation[T])(implicit numeric: Numeric[T]) {
    def min(value: T): ValidationWithValidators[T]   = field.is(NumberValidators.min(value))
    def max(value: T): ValidationWithValidators[T]   = field.is(NumberValidators.max(value))
    def equal(value: T): ValidationWithValidators[T] = field.is(NumberValidators.equalNumber(value))
  }

}

object NumberImplicits extends NumberImplicits
