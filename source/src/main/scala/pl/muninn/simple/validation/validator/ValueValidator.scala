package pl.muninn.simple.validation.validator

import cats.implicits.catsSyntaxValidatedIdBinCompat0

import pl.muninn.simple.validation.model.{InvalidField, ValidationSchemaContext}
import pl.muninn.simple.validation.{ValidationResult, ValidationSchema}

/** Represents validator for specific type of value [T]
  */
trait ValueValidator[-T] {
  def validate(key: String, value: T): ValidationResult

  def contramap[A](f: A => T): ValueValidator[A] = ValueValidator.instance[A] { case (key, value) =>
    this.validate(key, f(value))
  }
}

object ValueValidator {

  def validate[T](name: String, value: T, fieldValidator: ValueValidator[T]): ValidationResult =
    fieldValidator.validate(name, value)

  def instance[T](f: => (String, T) => ValidationResult): ValueValidator[T] = new ValueValidator[T] {
    override def validate(key: String, value: T): ValidationResult = f(key, value)
  }

  def fromSchema[T](schema: ValidationSchema[T]): ValueValidator[T] = instance[T] { (key, value) =>
    schema
      .apply(ValidationSchemaContext.apply(value))
      .map(value => value.withKey(key))
      .validate
  }

  val valid: ValidationResult                        = ().validNec
  def invalid(error: InvalidField): ValidationResult = error.invalidNec
}
