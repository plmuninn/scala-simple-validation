package pl.muninn.simple.validation.validators

import pl.muninn.simple.validation.ValueValidator.{invalid, valid}
import pl.muninn.simple.validation.{InvalidField, ValueValidator}

object CommonValidators {

  type EqualMagnetic[T] = (T, T) => Boolean

  type EmptyMagnetic[T] = T => Boolean

  type LengthMagnetic[T] = T => Int

  def equal[T](expected: T)(implicit equal: EqualMagnetic[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    if (equal(value, expected)) valid else invalid(InvalidField.EqualValue(key, expected, value))
  }

  def empty[T](implicit empty: EmptyMagnetic[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    if (empty(value)) valid else invalid(InvalidField.ExpectedEmpty(key))
  }

  def notEmpty[T](implicit emptyMagnetic: EmptyMagnetic[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    if (emptyMagnetic(value)) invalid(InvalidField.EmptyField(key)) else valid
  }

  def minimalLength[T](expected: Int)(implicit length: LengthMagnetic[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    val valueLength = length(value)
    if (valueLength >= expected) valid else invalid(InvalidField.MinimalLength(key, expected, valueLength))
  }

  def maximalLength[T](expected: Int)(implicit length: LengthMagnetic[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    val valueLength = length(value)
    if (valueLength <= expected) valid else invalid(InvalidField.MaximalLength(key, expected, valueLength))
  }

  def exactLength[T](expected: Int)(implicit length: LengthMagnetic[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    val valueLength = length(value)
    if (valueLength == expected) valid else invalid(InvalidField.ExpectedLength(key, expected, valueLength))
  }
}
