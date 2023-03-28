package pl.muninn.simple.validation.validator.magnetic

import cats.data.NonEmptyList

import pl.muninn.simple.validation.model.{Field, FieldValidator}
import pl.muninn.simple.validation.validator.ValueValidator
import pl.muninn.simple.validation.validator.typed.OptionValidators

trait OptionImplicits {

  implicit class OptionValidation[T](field: Field[Option[T]]) {

    def ifDefined(fieldValidator: NonEmptyList[ValueValidator[T]]): FieldValidator[Option[T]] =
      field.is(OptionValidators.ifDefined[T](fieldValidator))

    def notEmptyAnd(fieldValidator: NonEmptyList[ValueValidator[T]]): FieldValidator[Option[T]] =
      notEmpty.and(OptionValidators.ifDefined[T](fieldValidator))

    def definedAnd(fieldValidator: NonEmptyList[ValueValidator[T]]): FieldValidator[Option[T]] =
      notEmptyAnd(fieldValidator)

    def empty: FieldValidator[Option[T]]      = field.is(OptionValidators.notDefined[T])
    def notDefined: FieldValidator[Option[T]] = empty

    def notEmpty: FieldValidator[Option[T]]  = field.is(OptionValidators.defined[T])
    def isDefined: FieldValidator[Option[T]] = notEmpty
  }
}

object OptionImplicits extends OptionImplicits
