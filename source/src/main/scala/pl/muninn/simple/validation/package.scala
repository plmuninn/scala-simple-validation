package pl.muninn.simple

import scala.language.implicitConversions

import cats.data.{NonEmptyList, ValidatedNec}

import pl.muninn.simple.validation.model.{FieldValidator, InvalidField, ValidationSchemaContext}
import pl.muninn.simple.validation.validator.ValueValidator

package object validation extends ValidationImplicits with TypedValidators with MagneticValidators {

  type ValidationSchema[T] = ValidationSchemaContext[T] => NonEmptyList[FieldValidator[_]]

  type ValidationResult = ValidatedNec[InvalidField, Unit]
  def createSchema[T](f: ValidationSchemaContext[T] => NonEmptyList[FieldValidator[_]]): ValidationSchema[T] =
    ValidationSchema.createSchema(f)

  val valid: ValidationResult                   = ValueValidator.valid
  def invalid: InvalidField => ValidationResult = ValueValidator.invalid

}
