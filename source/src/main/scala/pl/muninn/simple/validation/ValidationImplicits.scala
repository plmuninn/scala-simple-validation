package pl.muninn.simple.validation

import scala.language.implicitConversions

import cats.data.{NonEmptyList, ValidatedNec}

import pl.muninn.simple.validation.ValidationSchemaContext.ValidationSchema
import pl.muninn.simple.validation.model.ValidationWithValidators

trait ValidationImplicits {

  implicit def convertValidatorToList[T](result: ValueValidator[T]): NonEmptyList[ValueValidator[T]] = NonEmptyList.one(result)

  implicit def convertValidationWithValidatorsToList[T](result: ValidationWithValidators[T]): NonEmptyList[ValidationWithValidators[T]] =
    NonEmptyList.one(result)

  implicit def convertSchemaToValueValidator[T](schema: ValidationSchema[T]): ValueValidator[T] = ValueValidator.instance[T] { case (key, value) =>
    schema
      .apply(ValidationSchemaContext.apply(value))
      .map(value => value.withKey(key))
      .run
  }

  implicit class ValueValidatorOps[T](validator: ValueValidator[T]) {
    def and(otherValidator: ValueValidator[T]): NonEmptyList[ValueValidator[T]] = NonEmptyList.of(validator, otherValidator)

    def contramap[A](f: A => T): ValueValidator[A] = ValueValidator.instance[A] { case (key, value) =>
      validator.validate(key, f(value))
    }
  }

  implicit class ValueValidatorListOps[T](validators: NonEmptyList[ValueValidator[T]]) {
    def and(otherValidators: NonEmptyList[ValueValidator[T]]): NonEmptyList[ValueValidator[T]] = validators ::: otherValidators
  }

  implicit class SingleValidationWithValidatorOps(result: ValidationWithValidators[_]) {
    def +(otherResult: ValidationWithValidators[_]): NonEmptyList[ValidationWithValidators[_]] = NonEmptyList(result, List(otherResult))
    def +(otherResults: NonEmptyList[ValidationWithValidators[_]]): NonEmptyList[ValidationWithValidators[_]] =
      NonEmptyList.one(result).concatNel(otherResults)
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

    def asValidator: ValueValidator[T] = convertSchemaToValueValidator(schema)
  }

  implicit class SchemaOps[T](schema: ValidationSchema[T]) {
    def validate(value: T): ValidatedNec[InvalidField, Unit] = schema.apply(ValidationSchemaContext(value)).run

    def asValidator: ValueValidator[T] = convertSchemaToValueValidator(schema)
  }

  implicit class CollectionOfValidationOps[T](validators: NonEmptyList[ValueValidator[T]]) {
    def runAndCombine(key: String, value: T): ValidatedNec[InvalidField, Unit] =
      validators.tail.foldLeft(validators.head.validate(key, value))((acc, validator) => acc combine validator.validate(key, value))
  }
}

private[validation] object ValidationImplicits extends ValidationImplicits
