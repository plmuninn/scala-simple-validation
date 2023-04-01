package pl.muninn.simple.validation.model

import cats.data.NonEmptyList

import pl.muninn.simple.validation.validator.ValueValidator
import pl.muninn.simple.validation.{CollectionOfValidationOps, ValidationResult}

/** Represents field to validate with it's lists of validators
  */
class FieldValidator[T](field: Field[T], validators: NonEmptyList[ValueValidator[T]]) {

  def and(nextValidator: ValueValidator[T]): FieldValidator[T] =
    new FieldValidator[T](field = field, validators = validators.concatNel(NonEmptyList.of(nextValidator)))

  def merge(nextValidators: NonEmptyList[ValueValidator[T]]) = new FieldValidator[T](field = field, validators = validators ++ nextValidators.toList)

  def withKey(value: String): FieldValidator[T] = new FieldValidator[T](field.withKey(value), validators)

  def validate: ValidationResult = validators.runAndCombine(field.key, field.value)
}
