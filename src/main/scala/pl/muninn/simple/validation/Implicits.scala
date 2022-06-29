package pl.muninn.simple.validation

import scala.language.implicitConversions

import cats.data.{NonEmptyList, ValidatedNec}

import pl.muninn.simple.validation.ValidationSchemaContext.ValidationSchema
import pl.muninn.simple.validation.model.ValidationWithValidators

private[validation] trait Implicits {

  implicit def convertValidatorToList[T](result: ValueValidator[T]): NonEmptyList[ValueValidator[T]] = NonEmptyList.one(result)

  implicit def convertValidationWithValidatorsToList[T](result: ValidationWithValidators[T]): NonEmptyList[ValidationWithValidators[T]] =
    NonEmptyList.one(result)

  implicit class ValueValidatorOps[T](validator: ValueValidator[T]) {
    def and(otherValidator: ValueValidator[T]): NonEmptyList[ValueValidator[T]] = NonEmptyList.of(validator, otherValidator)
  }

  implicit class SingleValidationWithValidatorOps(result: ValidationWithValidators[_]) {
    def +(otherResult: ValidationWithValidators[_]): NonEmptyList[ValidationWithValidators[_]] = NonEmptyList(result, List(otherResult))
  }

  implicit class ValidationWithValidatorsListOps(result: NonEmptyList[ValidationWithValidators[_]]) {
    def +(otherResult: ValidationWithValidators[_]): NonEmptyList[ValidationWithValidators[_]] = result.concatNel(otherResult)

    def +(otherResults: NonEmptyList[ValidationWithValidators[_]]): NonEmptyList[ValidationWithValidators[_]] = result.concatNel(otherResults)

    def run: ValidatedNec[InvalidField, Unit] =
      result.tail.foldLeft(result.head.validate) { case (acc, validator) =>
        acc.combine(validator.validate)
      }
  }

  implicit class ValueSchemaOps[T](value: T)(implicit schema: ValidationSchema[T]) {
    def validate: ValidatedNec[InvalidField, Unit] = schema.apply(ValidationSchemaContext(value)).run
  }

  implicit class SchemaOps[T](schema: ValidationSchema[T]) {
    def validate(value: T): ValidatedNec[InvalidField, Unit] = schema.apply(ValidationSchemaContext(value)).run
  }

  implicit class CollectionOfValidationOps[T](validators: NonEmptyList[ValueValidator[T]]) {
    def runAndCombine(key: String, value: T): ValidatedNec[InvalidField, Unit] =
      validators.tail.foldLeft(validators.head.validate(key, value))((acc, validator) => acc combine validator.validate(key, value))
  }
}

private[validation] object Implicits extends Implicits
