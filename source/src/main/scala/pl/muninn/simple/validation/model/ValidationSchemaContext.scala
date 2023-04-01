package pl.muninn.simple.validation.model

import scala.language.experimental.macros

import cats.data.NonEmptyList

import pl.muninn.simple.validation.ValidationImplicits
import pl.muninn.simple.validation.auto.FieldMacroImpl
import pl.muninn.simple.validation.validator.typed.AnyTypeValidators

/** Represents helping context for creation of validation structure
  */
trait ValidationSchemaContext[T] {
  def value: T

  def field[R](name: String)(f: => T => R): Field[R] =
    new Field[R](name, f(value))

  def field[R](f: T => R): Field[R] = macro FieldMacroImpl.fieldSelector[T, R]

  def pair[R](firstName: String)(f: T => R)(secondName: String)(f2: T => R): Field[(R, R)] =
    new Field[(R, R)](s"$firstName and $secondName", (f(value), f2(value)))

  def pair[R](f: T => R)(f2: T => R): Field[(R, R)] = macro FieldMacroImpl.pairSelector[T, R]

  def custom(f: => T => NonEmptyList[FieldValidator[_]]): NonEmptyList[FieldValidator[_]] = f(value)

  def noneValidator: FieldValidator[T] = (new Field[T]("noneValidator", value)).is(AnyTypeValidators.emptyValidator)
}

object ValidationSchemaContext {

  def apply[T](input: T): ValidationSchemaContext[T] = new ValidationSchemaContext[T] {
    lazy val value: T = input
  }

}
