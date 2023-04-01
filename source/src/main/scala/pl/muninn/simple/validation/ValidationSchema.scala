package pl.muninn.simple.validation

import cats.data.NonEmptyList

import pl.muninn.simple.validation.model.{FieldValidator, ValidationSchemaContext}

object ValidationSchema {
  def createSchema[T](f: ValidationSchemaContext[T] => NonEmptyList[FieldValidator[_]]): ValidationSchema[T] = f
}
