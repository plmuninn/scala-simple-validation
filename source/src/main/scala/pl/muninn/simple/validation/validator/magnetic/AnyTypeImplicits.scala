package pl.muninn.simple.validation.validator.magnetic

import pl.muninn.simple.validation.model.{Field, FieldValidator}
import pl.muninn.simple.validation.validator.typed.AnyTypeValidators
import pl.muninn.simple.validation.validator.typed.AnyTypeValidators.customValid

trait AnyTypeImplicits {

  implicit class AnyValidation[T](field: Field[T]) {

    def equalValue(expected: T): FieldValidator[T] = field.is(AnyTypeValidators.equalValue[T](expected))

    def custom(code: String, reason: String)(f: T => Boolean): FieldValidator[T] =
      field.is(customValid(code, { _: T => reason }, Map.empty)(f))

    def custom(code: String, reason: String, metadata: Map[String, String])(f: T => Boolean): FieldValidator[T] =
      field.is(customValid(code, { _: T => reason }, metadata)(f))

    def custom(code: String, reason: T => String)(f: T => Boolean): FieldValidator[T] =
      field.is(customValid(code, reason, Map.empty)(f))

    def custom(code: String, reason: T => String, metadata: Map[String, String])(f: T => Boolean): FieldValidator[T] =
      field.is(customValid(code, reason, metadata)(f))
  }

  implicit class PairValidation[T](field: Field[(T, T)]) {
    def fieldsEqual: FieldValidator[(T, T)] = field.is(AnyTypeValidators.fieldsEqual[T])
  }
}

object AnyTypeImplicits extends AnyTypeImplicits
