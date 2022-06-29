package pl.muninn.simple.validation.composition

import cats.data.NonEmptyList

import pl.muninn.simple.validation.ValueValidator
import pl.muninn.simple.validation.model.{Validation, ValidationWithValidators}
import pl.muninn.simple.validation.validators.OptionValidators.{forOpt, isDefined, notDefined}

trait OptionComposition {
  implicit class OptionValidation[T](field: Validation[Option[T]]) {
    def ifDefined(fieldValidator: NonEmptyList[ValueValidator[T]]): ValidationWithValidators[Option[T]]  = field.is(forOpt[T](fieldValidator))
    def definedAnd(fieldValidator: NonEmptyList[ValueValidator[T]]): ValidationWithValidators[Option[T]] = defined.and(forOpt[T](fieldValidator))
    def defined: ValidationWithValidators[Option[T]]                                                     = field.is(isDefined[T])
    def undefined: ValidationWithValidators[Option[T]]                                                   = field.is(notDefined[T])
  }
}

object OptionComposition extends OptionComposition
