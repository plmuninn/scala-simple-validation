package pl.muninn.simple.validation.model

import cats.data.NonEmptyList

import pl.muninn.simple.validation.ValidationSchema
import pl.muninn.simple.validation.validator.ValueValidator

/** Represents middle step of validation - Field that you can attach validators for field value
  */
class Field[T](val key: String, val value: T) {

  def is(validators: NonEmptyList[ValueValidator[T]]): FieldValidator[T] = new FieldValidator[T](this, validators)
  def is(validator: ValueValidator[T]): FieldValidator[T]                = new FieldValidator[T](this, NonEmptyList.of(validator))
  def is(schemaContext: ValidationSchema[T]): FieldValidator[T]          = is(ValueValidator.fromSchema[T](schemaContext))
  def withKey(newKey: String): Field[T]                                  = new Field[T](s"$newKey.$key", value)

}
