package pl.muninn.simple.validation.model

import cats.data.NonEmptyList

import pl.muninn.simple.validation.ValueValidator

class Validation[T](val key: String, val value: T) {
  def is(validators: NonEmptyList[ValueValidator[T]]): ValidationWithValidators[T] = new ValidationWithValidators[T](this, validators)
  def is(validator: ValueValidator[T]): ValidationWithValidators[T]                = new ValidationWithValidators[T](this, NonEmptyList.of(validator))
}
