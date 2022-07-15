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

  def allInList[A](validator: ValueValidator[A]): ValueValidator[List[A]]     = all(NonEmptyList.one(validator))
  def allInSeq[A](validator: ValueValidator[A]): ValueValidator[Seq[A]]       = all(NonEmptyList.one(validator))
  def allInSet[A](validator: ValueValidator[A]): ValueValidator[Set[A]]       = all(NonEmptyList.one(validator))
  def allInVector[A](validator: ValueValidator[A]): ValueValidator[Vector[A]] = all(NonEmptyList.one(validator))

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

  def allInList[A](validators: NonEmptyList[ValueValidator[A]]): ValueValidator[List[A]]     = all(validators)
  def allInSeq[A](validators: NonEmptyList[ValueValidator[A]]): ValueValidator[Seq[A]]       = all(validators)
  def allInSet[A](validators: NonEmptyList[ValueValidator[A]]): ValueValidator[Set[A]]       = all(validators)
  def allInVector[A](validators: NonEmptyList[ValueValidator[A]]): ValueValidator[Vector[A]] = all(validators)

  private implicit def collectionEmptyMagnetic[A, CC[x] <: Iterable[x]]: EmptyMagnetic[IterableOps[A, CC, CC[A]]] = _.isEmpty

  private implicit def collectionLengthMagnetic[A, CC[x] <: Iterable[x]]: LengthMagnetic[IterableOps[A, CC, CC[A]]] = _.size

  def emptyCollection[A, CC[x] <: Iterable[x]]: ValueValidator[IterableOps[A, CC, CC[A]]] = CommonValidators.empty

  def emptyList[A]: ValueValidator[List[A]]     = emptyCollection
  def emptySeq[A]: ValueValidator[Seq[A]]       = emptyCollection
  def emptySet[A]: ValueValidator[Set[A]]       = emptyCollection
  def emptyVector[A]: ValueValidator[Vector[A]] = emptyCollection

  def notEmptyCollection[A, CC[x] <: Iterable[x]]: ValueValidator[IterableOps[A, CC, CC[A]]] = CommonValidators.notEmpty

  def notEmptyList[A]: ValueValidator[List[A]]     = notEmptyCollection
  def notEmptySeq[A]: ValueValidator[Seq[A]]       = notEmptyCollection
  def notEmptySet[A]: ValueValidator[Set[A]]       = notEmptyCollection
  def notEmptyVector[A]: ValueValidator[Vector[A]] = notEmptyCollection

  def minimalLengthCollection[A, CC[x] <: Iterable[x]](expected: Int): ValueValidator[IterableOps[A, CC, CC[A]]] =
    CommonValidators.minimalLength(expected)

  def minimalLengthList[A](expected: Int): ValueValidator[List[A]]     = minimalLengthCollection(expected)
  def minimalLengthSeq[A](expected: Int): ValueValidator[Seq[A]]       = minimalLengthCollection(expected)
  def minimalLengthSet[A](expected: Int): ValueValidator[Set[A]]       = minimalLengthCollection(expected)
  def minimalLengthVector[A](expected: Int): ValueValidator[Vector[A]] = minimalLengthCollection(expected)

  def maximalLengthCollection[A, CC[x] <: Iterable[x]](expected: Int): ValueValidator[IterableOps[A, CC, CC[A]]] =
    CommonValidators.maximalLength(expected)

  def maximalLengthList[A](expected: Int): ValueValidator[List[A]]     = maximalLengthCollection(expected)
  def maximalLengthSeq[A](expected: Int): ValueValidator[Seq[A]]       = maximalLengthCollection(expected)
  def maximalLengthSet[A](expected: Int): ValueValidator[Set[A]]       = maximalLengthCollection(expected)
  def maximalLengthVector[A](expected: Int): ValueValidator[Vector[A]] = maximalLengthCollection(expected)

  def exactLengthCollection[A, CC[x] <: Iterable[x]](expected: Int): ValueValidator[IterableOps[A, CC, CC[A]]] =
    CommonValidators.exactLength(expected)

  def exactLengthList[A](expected: Int): ValueValidator[List[A]]     = exactLengthCollection(expected)
  def exactLengthSeq[A](expected: Int): ValueValidator[Seq[A]]       = exactLengthCollection(expected)
  def exactLengthSet[A](expected: Int): ValueValidator[Set[A]]       = exactLengthCollection(expected)
  def exactLengthVector[A](expected: Int): ValueValidator[Vector[A]] = exactLengthCollection(expected)
}

object CollectionValidators extends CollectionValidators
