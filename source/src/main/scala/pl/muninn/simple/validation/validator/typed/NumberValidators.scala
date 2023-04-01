package pl.muninn.simple.validation.validator.typed

import pl.muninn.simple.validation.failures.numeric.{MaximalValue, MinimalValue}
import pl.muninn.simple.validation.validator.ValueValidator
import pl.muninn.simple.validation.validator.ValueValidator.{invalid, valid}
import pl.muninn.simple.validation.validator.typed.CommonValidators.EqualMagnetic

trait NumberValidators {

  private implicit def numberEqualMagnetic[T](implicit numeric: Numeric[T]): EqualMagnetic[T] = { case (value, expected) =>
    numeric.equiv(value, expected)
  }

  def equalNumber[T](expected: T)(implicit numeric: Numeric[T]): ValueValidator[T] = CommonValidators.equal(expected)

  def min[T](expected: T)(implicit numeric: Numeric[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    if (numeric.gteq(value, expected)) valid else invalid(MinimalValue(key, expected, value))
  }

  def max[T](expected: T)(implicit numeric: Numeric[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    if (numeric.lteq(value, expected)) valid else invalid(MaximalValue(key, expected, value))
  }
}

object NumberValidators extends NumberValidators
