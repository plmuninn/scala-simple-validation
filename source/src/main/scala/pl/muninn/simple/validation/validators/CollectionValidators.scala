package pl.muninn.simple.validation.validators

import scala.collection.IterableOps
import scala.language.implicitConversions

import cats.data.NonEmptyList

import pl.muninn.simple.validation.ValueValidator
import pl.muninn.simple.validation.ValueValidator.valid
import pl.muninn.simple.validation.validators.CommonValidators.{EmptyMagnetic, LengthMagnetic}

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

  private implicit def collectionEmptyMagnetic[A, CC[x] <: Iterable[x]]: EmptyMagnetic[IterableOps[A, CC, CC[A]]] = _.isEmpty

  private implicit def collectionLengthMagnetic[A, CC[x] <: Iterable[x]]: LengthMagnetic[IterableOps[A, CC, CC[A]]] = _.size

  def emptyCollection[A, CC[x] <: Iterable[x]]: ValueValidator[IterableOps[A, CC, CC[A]]] = CommonValidators.empty

  def notEmptyCollection[A, CC[x] <: Iterable[x]]: ValueValidator[IterableOps[A, CC, CC[A]]] = CommonValidators.notEmpty

  def minimalLengthCollection[A, CC[x] <: Iterable[x]](expected: Int): ValueValidator[IterableOps[A, CC, CC[A]]] =
    CommonValidators.minimalLength(expected)

  def maximalLengthCollection[A, CC[x] <: Iterable[x]](expected: Int): ValueValidator[IterableOps[A, CC, CC[A]]] =
    CommonValidators.maximalLength(expected)

  def exactLengthCollection[A, CC[x] <: Iterable[x]](expected: Int): ValueValidator[IterableOps[A, CC, CC[A]]] =
    CommonValidators.exactLength(expected)
}

object CollectionValidators extends CollectionValidators
