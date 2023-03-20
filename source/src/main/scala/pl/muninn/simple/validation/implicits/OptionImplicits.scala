package pl.muninn.simple.validation.implicits

import cats.data.NonEmptyList

import pl.muninn.simple.validation.ValueValidator
import pl.muninn.simple.validation.model.{Validation, ValidationWithValidators}
import pl.muninn.simple.validation.validators.OptionValidators

trait OptionImplicits {

  implicit class OptionValidation[T](field: Validation[Option[T]]) {

    def ifDefined(fieldValidator: NonEmptyList[ValueValidator[T]]): ValidationWithValidators[Option[T]] =
      field.is(OptionValidators.ifDefined[T](fieldValidator))

    def notEmptyAnd(fieldValidator: NonEmptyList[ValueValidator[T]]): ValidationWithValidators[Option[T]] =
      notEmpty.and(OptionValidators.ifDefined[T](fieldValidator))

    def definedAnd(fieldValidator: NonEmptyList[ValueValidator[T]]): ValidationWithValidators[Option[T]] =
      notEmptyAnd(fieldValidator)

    def empty: ValidationWithValidators[Option[T]]      = field.is(OptionValidators.notDefined[T])
    def notDefined: ValidationWithValidators[Option[T]] = empty

    def notEmpty: ValidationWithValidators[Option[T]]  = field.is(OptionValidators.defined[T])
    def isDefined: ValidationWithValidators[Option[T]] = notEmpty
  }
}

object OptionImplicits extends OptionImplicits
