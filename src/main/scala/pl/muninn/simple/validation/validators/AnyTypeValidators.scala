package pl.muninn.simple.validation.validators

import pl.muninn.simple.validation.InvalidField.FieldsNotEqual
import pl.muninn.simple.validation.ValueValidator.{invalid, valid}
import pl.muninn.simple.validation.{InvalidField, ValueValidator}

trait AnyTypeValidators {

  val emptyValidator: ValueValidator[Any] = ValueValidator.instance { (_, _: Any) => valid }

  def equalValue[T](expected: T): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    if (value == expected) valid else invalid(InvalidField.EqualValue(key, expected, value))
  }

  def customValid[T](code: String, reason: T => String)(f: T => Boolean): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    if (f(value)) valid else invalid(InvalidField.custom(key, reason(value), code))
  }

  def fieldsEqual[T]: ValueValidator[(T, T)] = ValueValidator.instance[(T, T)] { case (key, (field1, field2)) =>
    if (field1 != field2) invalid(FieldsNotEqual(key)) else valid
  }
}

object AnyTypeValidators extends AnyTypeValidators
