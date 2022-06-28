package pl.muninn.simple.validation

import scala.collection.IterableOps

import cats.data.NonEmptyList

import pl.muninn.simple.validation.InvalidField.FieldsNotEqual

object CommonValidators {

  import ValueValidator._

  def equalValue[T](expected: T): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    if (value == expected) valid else invalid(InvalidField.EqualValue(key, expected, value))
  }

  def customValid[T](code: String, reason: T => String)(f: T => Boolean): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    if (f(value)) valid else invalid(InvalidField.custom(key, code, reason(value)))
  }

  val nonEmptyString: ValueValidator[String] = ValueValidator.instance { (key, value) =>
    if (value.isBlank) invalid(InvalidField.EmptyField(key)) else valid
  }

  private val emailRegex =
    """^[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$""".r

  val emailString: ValueValidator[String] = ValueValidator.instance { (key, value) =>
    if (!emailRegex.matches(value)) invalid(InvalidField.NotEmail(key)) else valid
  }

  private val complexPasswordRegex =
    """^(?:(?=.*[a-z])(?:(?=.*[A-Z])(?=.*[\d\W])|(?=.*\W)(?=.*\d))|(?=.*\W)(?=.*[A-Z])(?=.*\d)).{8,}$""".r

  val complexPassword: ValueValidator[String] = ValueValidator.instance { (key, value) =>
    if (!complexPasswordRegex.matches(value)) invalid(InvalidField.NotComplexPassword(key)) else valid
  }

  def stringMinimalLength(expected: Int): ValueValidator[String] = ValueValidator.instance { (key, value) =>
    if (value.length >= expected) valid else invalid(InvalidField.MinimalLength(key, expected, value.length))
  }

  def stringMaximalLength(expected: Int): ValueValidator[String] = ValueValidator.instance { (key, value) =>
    if (value.length <= expected) valid else invalid(InvalidField.MaximalLength(key, expected, value.length))
  }

  def stringLength(expected: Int): ValueValidator[String] = ValueValidator.instance { (key, value) =>
    if (value.length == expected) valid else invalid(InvalidField.ExpectedLength(key, expected, value.length))
  }

  def fieldsEqual[T]: ValueValidator[(T, T)] = ValueValidator.instance[(T, T)] { case (key, (field1, field2)) =>
    if (field1 != field2) invalid(FieldsNotEqual(key)) else valid
  }

  def forOpt[T](validators: NonEmptyList[ValueValidator[T]]): ValueValidator[Option[T]] = ValueValidator.instance[Option[T]] { (key, value) =>
    value.fold(valid)(value => validators.runAndCombine(key, value))
  }

  def forOpt[T](validators: ValueValidator[T]): ValueValidator[Option[T]] =
    forOpt[T](NonEmptyList.of(validators))

  def isDefined[T]: ValueValidator[Option[T]] = ValueValidator.instance { (key, value) =>
    if (value.isDefined) valid else invalid(InvalidField.EmptyField(key))
  }

  def notDefined[T]: ValueValidator[Option[T]] = ValueValidator.instance { (key, value) =>
    if (value.isEmpty) valid else invalid(InvalidField.ExpectedEmpty(key))
  }

  def all[A, CC[x] <: Iterable[x]](validator: ValueValidator[A]): ValueValidator[IterableOps[A, CC, CC[A]]] =
    all(NonEmptyList.one(validator))

  def all[A, CC[x] <: Iterable[x]](
      validators: NonEmptyList[ValueValidator[A]]
  ): ValueValidator[IterableOps[A, CC, CC[A]]] = ValueValidator.instance { (key, value) =>
    val validations =
      value.zipWithIndex.map { case (value, index) =>
        validators.runAndCombine(s"$key.$index", value)
      }

    validations.reduce(_ combine _)
  }

  def noneEmptyCollection[A, CC[x] <: Iterable[x]]: ValueValidator[IterableOps[A, CC, CC[A]]] =
    ValueValidator.instance { (key, value) =>
      if (value.isEmpty) invalid(InvalidField.EmptyField(key)) else valid
    }

  def emptyCollection[A, CC[x] <: Iterable[x]]: ValueValidator[IterableOps[A, CC, CC[A]]] =
    ValueValidator.instance { (key, value) =>
      if (value.isEmpty) valid else invalid(InvalidField.ExpectedEmpty(key))
    }

  def collectionMinimalLength[A, CC[x] <: Iterable[x]](expected: Int): ValueValidator[IterableOps[A, CC, CC[A]]] =
    ValueValidator.instance { (key, value) =>
      if (value.size >= expected) valid else invalid(InvalidField.MinimalLength(key, expected, value.size))
    }

  def collectionMaximalLength[A, CC[x] <: Iterable[x]](expected: Int): ValueValidator[IterableOps[A, CC, CC[A]]] = ValueValidator.instance {
    (key, value) =>
      if (value.size <= expected) valid else invalid(InvalidField.MaximalLength(key, expected, value.size))
  }

  def collectionLength[A, CC[x] <: Iterable[x]](expected: Int): ValueValidator[IterableOps[A, CC, CC[A]]] = ValueValidator.instance { (key, value) =>
    if (value.size == expected) valid else invalid(InvalidField.ExpectedLength(key, expected, value.size))
  }

  def minimalNumber[T](expected: T)(implicit numeric: Numeric[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    if (numeric.gteq(value, expected)) valid else invalid(InvalidField.MinimalValue(key, expected, value))
  }

  def maximalNumber[T](expected: T)(implicit numeric: Numeric[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    if (numeric.lteq(value, expected)) valid else invalid(InvalidField.MaximalValue(key, expected, value))
  }

  def expectedNumber[T](expected: T)(implicit numeric: Numeric[T]): ValueValidator[T] = ValueValidator.instance { (key, value) =>
    if (numeric.equiv(value, expected)) valid else invalid(InvalidField.ExpectedValue(key, expected, value))
  }
}
