package pl.muninn.simple.validation.validators

import scala.collection.IterableOps
import scala.language.implicitConversions

import cats.data.NonEmptyList

import pl.muninn.simple.validation.ValueValidator.{invalid, valid}
import pl.muninn.simple.validation.{InvalidField, ValueValidator}

trait CollectionValidators {

  def all[A, CC[x] <: Iterable[x]](validator: ValueValidator[A]): ValueValidator[IterableOps[A, CC, CC[A]]] =
    all(NonEmptyList.one(validator))

  def all[A, CC[x] <: Iterable[x]](
      validators: NonEmptyList[ValueValidator[A]]
  ): ValueValidator[IterableOps[A, CC, CC[A]]] = ValueValidator.instance { (key, value) =>
    val validations =
      value.zipWithIndex.map { case (value, index) =>
        validators.runAndCombine(s"$key.$index", value)
      }

    validations.reduceOption(_ combine _) match {
      case Some(value) => value
      case None        => valid
    }
  }

  def noneEmptyCollection[A, CC[x] <: Iterable[x]]: ValueValidator[IterableOps[A, CC, CC[A]]] =
    ValueValidator.instance { (key, value) =>
      if (value.iterator.isEmpty) invalid(InvalidField.EmptyField(key)) else valid
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

}

object CollectionValidators extends CollectionValidators
