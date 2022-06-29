package pl.muninn.simple.validation.composition

import pl.muninn.simple.validation.model.{Validation, ValidationWithValidators}
import pl.muninn.simple.validation.validators.AnyTypeValidators.{customValid, equalValue, fieldsEqual}

trait AnyTypeComposition {

  implicit class AnyValidation[T](validation: Validation[T]) {
    def equal(expected: T): ValidationWithValidators[T]                                    = validation.is(equalValue(expected))
    def custom(code: String, reason: String)(f: T => Boolean): ValidationWithValidators[T] = validation.is(customValid(code, { _: T => reason })(f))
    def custom(code: String, reason: T => String)(f: T => Boolean): ValidationWithValidators[T] = validation.is(customValid(code, reason)(f))
  }

  implicit class PairValidation[T](field: Validation[(T, T)]) {
    def equalValues: ValidationWithValidators[(T, T)] = field.is(fieldsEqual[T])
  }
}

object AnyTypeComposition extends AnyTypeComposition
