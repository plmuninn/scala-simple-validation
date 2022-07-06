package pl.muninn.simple.validation.implicits

import pl.muninn.simple.validation.model.{Validation, ValidationWithValidators}
import pl.muninn.simple.validation.validators.AnyTypeValidators
import pl.muninn.simple.validation.validators.AnyTypeValidators.customValid

trait AnyTypeImplicits {

  implicit class AnyValidation[T](validation: Validation[T]) {

    def equalValue(expected: T): ValidationWithValidators[T] = validation.is(AnyTypeValidators.equalValue[T](expected))

    def custom(code: String, reason: String)(f: T => Boolean): ValidationWithValidators[T] =
      validation.is(customValid(code, { _: T => reason }, Map.empty)(f))

    def custom(code: String, reason: String, metadata: Map[String, String])(f: T => Boolean): ValidationWithValidators[T] =
      validation.is(customValid(code, { _: T => reason }, metadata)(f))

    def custom(code: String, reason: T => String)(f: T => Boolean): ValidationWithValidators[T] =
      validation.is(customValid(code, reason, Map.empty)(f))

    def custom(code: String, reason: T => String, metadata: Map[String, String])(f: T => Boolean): ValidationWithValidators[T] =
      validation.is(customValid(code, reason, metadata)(f))
  }

  implicit class PairValidation[T](field: Validation[(T, T)]) {
    def fieldsEqual: ValidationWithValidators[(T, T)] = field.is(AnyTypeValidators.fieldsEqual[T])
  }
}

object AnyTypeImplicits extends AnyTypeImplicits
