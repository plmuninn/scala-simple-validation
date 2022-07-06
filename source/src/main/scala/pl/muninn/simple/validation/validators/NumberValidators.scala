package pl.muninn.simple.validation.validators

import pl.muninn.simple.validation.ValueValidator.{invalid, valid}
import pl.muninn.simple.validation.validators.CommonValidators.EqualMagnetic
import pl.muninn.simple.validation.{InvalidField, ValueValidator}

trait NumberValidators {

  private implicit def numberEqualMagnetic[T](implicit numeric: Numeric[T]): EqualMagnetic[T] = { case (value, expected) =>
    numeric.equiv(value, expected)
  }

  def equalNumber[T](expected: T)(implicit numeric: Numeric[T]): ValueValidator[T] = CommonValidators.equal(expected)

  def min[T](expected: T)(implicit numeric: Numeric[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    if (numeric.gteq(value, expected)) valid else invalid(InvalidField.MinimalValue(key, expected, value))
  }

  def max[T](expected: T)(implicit numeric: Numeric[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    if (numeric.lteq(value, expected)) valid else invalid(InvalidField.MaximalValue(key, expected, value))
  }
}

object NumberValidators extends NumberValidators
