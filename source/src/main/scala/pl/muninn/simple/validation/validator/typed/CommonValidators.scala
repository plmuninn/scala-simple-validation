package pl.muninn.simple.validation.validator.typed

import pl.muninn.simple.validation.failures.common.{EmptyField, EqualValue, ExpectedEmpty, ExpectedLength, MaximalLength, MinimalLength}
import pl.muninn.simple.validation.validator.ValueValidator
import pl.muninn.simple.validation.validator.ValueValidator.{invalid, valid}

object CommonValidators {

  type EqualMagnetic[T] = (T, T) => Boolean

  type EmptyMagnetic[T] = T => Boolean

  type LengthMagnetic[T] = T => Int

  def equal[T](expected: T)(implicit equal: EqualMagnetic[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    if (equal(value, expected)) valid else invalid(EqualValue(key, expected, value))
  }

  def empty[T](implicit empty: EmptyMagnetic[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    if (empty(value)) valid else invalid(ExpectedEmpty(key))
  }

  def notEmpty[T](implicit emptyMagnetic: EmptyMagnetic[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    if (emptyMagnetic(value)) invalid(EmptyField(key)) else valid
  }

  def minimalLength[T](expected: Int)(implicit length: LengthMagnetic[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    val valueLength = length(value)
    if (valueLength >= expected) valid else invalid(MinimalLength(key, expected, valueLength))
  }

  def maximalLength[T](expected: Int)(implicit length: LengthMagnetic[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    val valueLength = length(value)
    if (valueLength <= expected) valid else invalid(MaximalLength(key, expected, valueLength))
  }

  def exactLength[T](expected: Int)(implicit length: LengthMagnetic[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    val valueLength = length(value)
    if (valueLength == expected) valid else invalid(ExpectedLength(key, expected, valueLength))
  }
}
