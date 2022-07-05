package pl.muninn.simple

import scala.language.implicitConversions

import cats.data.{NonEmptyList, ValidatedNec}

import pl.muninn.simple.validation.ValidationSchemaContext.ValidationSchema
import pl.muninn.simple.validation.model.ValidationWithValidators

package object validation {

  object all extends ValidationImplicits with Validators with TypeImplicits {
    type Schema[T] = ValidationSchema[T]

    def createSchema[T](f: ValidationSchemaContext[T] => NonEmptyList[ValidationWithValidators[_]]): Schema[T] =
      ValidationSchemaContext.createSchema(f)

    val valid: ValidatedNec[InvalidField, Unit]                   = ValueValidator.valid
    def invalid: InvalidField => ValidatedNec[InvalidField, Unit] = ValueValidator.invalid
  }

}
