package pl.muninn.simple.validation

import cats.data.NonEmptyList

import pl.muninn.simple.validation.model.{Validation, ValidationWithValidators}

trait ValidationSchemaContext[T] {
  def value: T

  def field[R](name: String)(f: => T => R): Validation[R] =
    new Validation[R](name, f(value))

  def pair[R](firstName: String)(f: T => R)(secondName: String)(f2: T => R): Validation[(R, R)] =
    new Validation[(R, R)](s"$firstName and $secondName", (f(value), f2(value)))

  def custom(f: => T => NonEmptyList[ValidationWithValidators[_]]): NonEmptyList[ValidationWithValidators[_]] = f(value)
}

object ValidationSchemaContext extends Implicits {

  type ValidationSchema[T] = ValidationSchemaContext[T] => NonEmptyList[ValidationWithValidators[_]]

  def apply[T](input: T): ValidationSchemaContext[T] = new ValidationSchemaContext[T] {
    lazy val value: T = input
  }

  def createSchema[T](f: ValidationSchemaContext[T] => NonEmptyList[ValidationWithValidators[_]]): ValidationSchema[T] = f
}
