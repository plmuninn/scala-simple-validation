package pl.muninn.simple.validation.validator.typed

import pl.muninn.simple.validation.failures.FieldsNotEqual
import pl.muninn.simple.validation.model.InvalidField
import pl.muninn.simple.validation.validator.ValueValidator
import pl.muninn.simple.validation.validator.ValueValidator.{invalid, valid}
import pl.muninn.simple.validation.validator.typed.CommonValidators.EqualMagnetic

trait AnyTypeValidators {

  val emptyValidator: ValueValidator[Any] = ValueValidator.instance { (_, _: Any) => valid }

  private implicit def anyEqualMagnetic[T]: EqualMagnetic[T] = { case (value, expected) => value == expected }

  def equalValue[T](expected: T): ValueValidator[T] = CommonValidators.equal(expected)

  def customValid[T](code: String, reason: T => String, metadata: Map[String, String] = Map.empty)(f: T => Boolean): ValueValidator[T] =
    ValueValidator.instance { (key, value) =>
      if (f(value)) valid else invalid(InvalidField.custom(key, reason(value), code, metadata))
    }

  def fieldsEqual[T]: ValueValidator[(T, T)] = ValueValidator.instance[(T, T)] { case (key, (field1, field2)) =>
    if (field1 != field2) invalid(FieldsNotEqual(key)) else valid
  }
}

object AnyTypeValidators extends AnyTypeValidators
