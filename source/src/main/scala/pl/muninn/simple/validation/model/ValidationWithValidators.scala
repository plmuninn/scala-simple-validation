package pl.muninn.simple.validation.model

import cats.data.{NonEmptyList, ValidatedNec}

import pl.muninn.simple.validation.{InvalidField, ValueValidator}

class ValidationWithValidators[T](field: Validation[T], validators: NonEmptyList[ValueValidator[T]]) {

  def and(nextValidator: ValueValidator[T]): ValidationWithValidators[T] = new ValidationWithValidators[T](
    field = field,
    validators = validators.concatNel(NonEmptyList.of(nextValidator))
  )

  def merge(nextValidators: NonEmptyList[ValueValidator[T]]) = new ValidationWithValidators[T](
    field = field,
    validators = validators ++ nextValidators.toList
  )

  def withKey(value: String): ValidationWithValidators[T] = new ValidationWithValidators[T](field.withKey(value), validators)

  def validate: ValidatedNec[InvalidField, Unit] =
    validators.runAndCombine(field.key, field.value)
}
