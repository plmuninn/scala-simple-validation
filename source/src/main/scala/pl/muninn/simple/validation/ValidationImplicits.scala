package pl.muninn.simple.validation

import scala.language.implicitConversions

import cats.data.NonEmptyList

import pl.muninn.simple.validation.model.{FieldValidator, ValidationSchemaContext}
import pl.muninn.simple.validation.validator.ValueValidator

trait ValidationImplicits {

  implicit def convertValidatorToList[T](result: ValueValidator[T]): NonEmptyList[ValueValidator[T]] = NonEmptyList.one(result)

  implicit def convertValidationWithValidatorsToList[T](result: FieldValidator[T]): NonEmptyList[FieldValidator[T]] =
    NonEmptyList.one(result)

  implicit def convertSchemaToValueValidator[T](schema: ValidationSchema[T]): ValueValidator[T] = ValueValidator.fromSchema[T](schema)

  implicit def convertSchemaToValueValidatorList[T](schema: ValidationSchema[T]): NonEmptyList[ValueValidator[T]] =
    NonEmptyList.one(convertSchemaToValueValidator(schema))

  implicit class ValueValidatorOps[T](validator: ValueValidator[T]) {
    def and(otherValidator: ValueValidator[T]): NonEmptyList[ValueValidator[T]] = NonEmptyList.of(validator, otherValidator)
  }

  implicit class ValueValidatorListOps[T](validators: NonEmptyList[ValueValidator[T]]) {
    def and(otherValidators: NonEmptyList[ValueValidator[T]]): NonEmptyList[ValueValidator[T]] = validators ::: otherValidators
  }

  implicit class SingleValidationWithValidatorOps(result: FieldValidator[_]) {
    def +(otherResult: FieldValidator[_]): NonEmptyList[FieldValidator[_]] = NonEmptyList(result, List(otherResult))

    def +(otherResults: NonEmptyList[FieldValidator[_]]): NonEmptyList[FieldValidator[_]] =
      NonEmptyList.one(result).concatNel(otherResults)
  }

  implicit class ValidationWithValidatorsListOps(result: NonEmptyList[FieldValidator[_]]) {
    def +(otherResult: FieldValidator[_]): NonEmptyList[FieldValidator[_]] = result.concatNel(otherResult)

    def +(otherResults: NonEmptyList[FieldValidator[_]]): NonEmptyList[FieldValidator[_]] = result.concatNel(otherResults)

    def validate: ValidationResult =
      result.tail.foldLeft(result.head.validate) { case (acc, validator) =>
        acc.combine(validator.validate)
      }
  }

  implicit class ValueSchemaOps[T](value: T)(implicit schema: ValidationSchema[T]) {
    def validate: ValidationResult = schema.apply(ValidationSchemaContext(value)).validate
  }

  implicit class SchemaOps[T](schema: ValidationSchema[T]) {
    def validate(value: T): ValidationResult = schema.apply(ValidationSchemaContext(value)).validate
  }

  implicit class CollectionOfValidationOps[T](validators: NonEmptyList[ValueValidator[T]]) {
    def runAndCombine(key: String, value: T): ValidationResult =
      validators.tail.foldLeft(validators.head.validate(key, value))((acc, validator) => acc combine validator.validate(key, value))
  }
}

object ValidationImplicits extends ValidationImplicits
