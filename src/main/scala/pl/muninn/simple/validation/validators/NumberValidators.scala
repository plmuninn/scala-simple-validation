package pl.muninn.simple.validation.validators

import pl.muninn.simple.validation.ValueValidator.{invalid, valid}
import pl.muninn.simple.validation.{InvalidField, ValueValidator}

trait NumberValidators {
  def minimalNumberValue[T](expected: T)(implicit numeric: Numeric[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    if (numeric.gteq(value, expected)) valid else invalid(InvalidField.MinimalValue(key, expected, value))
  }

  def maximalNumberValue[T](expected: T)(implicit numeric: Numeric[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    if (numeric.lteq(value, expected)) valid else invalid(InvalidField.MaximalValue(key, expected, value))
  }

  def numberEqual[T](expected: T)(implicit numeric: Numeric[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    if (numeric.equiv(value, expected)) valid else invalid(InvalidField.EqualValue(key, expected, value))
  }
}

object NumberValidators extends NumberValidators
