package pl.muninn.simple.validation

import cats.data.ValidatedNec
import cats.implicits.catsSyntaxValidatedIdBinCompat0

trait ValueValidator[-T] {

  type ValueType = T

  def validate(key: String, value: ValueType): ValidatedNec[InvalidField, Unit]
}

object ValueValidator extends ValidationImplicits {

  def validate[T](name: String, value: T, fieldValidator: ValueValidator[T]): ValidatedNec[InvalidField, Unit] =
    fieldValidator.validate(name, value)

  def instance[T](f: => (String, T) => ValidatedNec[InvalidField, Unit]): ValueValidator[T] = new ValueValidator[T] {
    override def validate(key: String, value: T): ValidatedNec[InvalidField, Unit] = f(key, value)
  }

  val valid: ValidatedNec[InvalidField, Unit]                        = ().validNec
  def invalid(error: InvalidField): ValidatedNec[InvalidField, Unit] = error.invalidNec
}
