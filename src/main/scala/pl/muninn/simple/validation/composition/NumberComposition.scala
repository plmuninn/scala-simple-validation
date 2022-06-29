package pl.muninn.simple.validation.composition

import pl.muninn.simple.validation.model.{Validation, ValidationWithValidators}
import pl.muninn.simple.validation.validators.NumberValidators.{maximalNumberValue, minimalNumberValue, numberEqual}

trait NumberComposition {
  implicit class NumberValidation[T](field: Validation[T])(implicit numeric: Numeric[T]) {
    def min(value: T): ValidationWithValidators[T]   = field.is(minimalNumberValue(value))
    def max(value: T): ValidationWithValidators[T]   = field.is(maximalNumberValue(value))
    def equal(value: T): ValidationWithValidators[T] = field.is(numberEqual(value))
  }
}

object NumberComposition extends NumberComposition
