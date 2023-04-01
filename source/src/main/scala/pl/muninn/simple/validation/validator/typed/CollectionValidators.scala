package pl.muninn.simple.validation.validator.typed

import scala.collection.IterableOps
import scala.language.implicitConversions

import cats.data.NonEmptyList

import pl.muninn.simple.validation.ValidationImplicits._
import pl.muninn.simple.validation.validator.ValueValidator
import pl.muninn.simple.validation.validator.ValueValidator.valid
import pl.muninn.simple.validation.validator.typed.CommonValidators.{EmptyMagnetic, LengthMagnetic}

trait CollectionValidators {

  def all[A, CC[x] <: Iterable[x]](validator: ValueValidator[A]): ValueValidator[IterableOps[A, CC, CC[A]]] =
    all(NonEmptyList.one(validator))

  def allInList[A](validator: ValueValidator[A]): ValueValidator[List[A]]     = all[A, List](NonEmptyList.one(validator))
  def allInSeq[A](validator: ValueValidator[A]): ValueValidator[Seq[A]]       = all[A, Seq](NonEmptyList.one(validator))
  def allInSet[A](validator: ValueValidator[A]): ValueValidator[Set[A]]       = all[A, Set](NonEmptyList.one(validator))
  def allInVector[A](validator: ValueValidator[A]): ValueValidator[Vector[A]] = all[A, Vector](NonEmptyList.one(validator))

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

  def allInList[A](validators: NonEmptyList[ValueValidator[A]]): ValueValidator[List[A]]     = all[A, List](validators)
  def allInSeq[A](validators: NonEmptyList[ValueValidator[A]]): ValueValidator[Seq[A]]       = all[A, Seq](validators)
  def allInSet[A](validators: NonEmptyList[ValueValidator[A]]): ValueValidator[Set[A]]       = all[A, Set](validators)
  def allInVector[A](validators: NonEmptyList[ValueValidator[A]]): ValueValidator[Vector[A]] = all[A, Vector](validators)

  private implicit def collectionEmptyMagnetic[A, CC[x] <: Iterable[x]]: EmptyMagnetic[IterableOps[A, CC, CC[A]]] = _.isEmpty

  private implicit def collectionLengthMagnetic[A, CC[x] <: Iterable[x]]: LengthMagnetic[IterableOps[A, CC, CC[A]]] = _.size

  def emptyCollection[A, CC[x] <: Iterable[x]]: ValueValidator[IterableOps[A, CC, CC[A]]] = CommonValidators.empty

  def emptyList[A]: ValueValidator[List[A]]     = emptyCollection[A, List]
  def emptySeq[A]: ValueValidator[Seq[A]]       = emptyCollection[A, Seq]
  def emptySet[A]: ValueValidator[Set[A]]       = emptyCollection[A, Set]
  def emptyVector[A]: ValueValidator[Vector[A]] = emptyCollection[A, Vector]

  def notEmptyCollection[A, CC[x] <: Iterable[x]]: ValueValidator[IterableOps[A, CC, CC[A]]] = CommonValidators.notEmpty

  def notEmptyList[A]: ValueValidator[List[A]]     = notEmptyCollection[A, List]
  def notEmptySeq[A]: ValueValidator[Seq[A]]       = notEmptyCollection[A, Seq]
  def notEmptySet[A]: ValueValidator[Set[A]]       = notEmptyCollection[A, Set]
  def notEmptyVector[A]: ValueValidator[Vector[A]] = notEmptyCollection[A, Vector]

  def minimalLengthCollection[A, CC[x] <: Iterable[x]](expected: Int): ValueValidator[IterableOps[A, CC, CC[A]]] =
    CommonValidators.minimalLength(expected)

  def minimalLengthList[A](expected: Int): ValueValidator[List[A]]     = minimalLengthCollection[A, List](expected)
  def minimalLengthSeq[A](expected: Int): ValueValidator[Seq[A]]       = minimalLengthCollection[A, Seq](expected)
  def minimalLengthSet[A](expected: Int): ValueValidator[Set[A]]       = minimalLengthCollection[A, Set](expected)
  def minimalLengthVector[A](expected: Int): ValueValidator[Vector[A]] = minimalLengthCollection[A, Vector](expected)

  def maximalLengthCollection[A, CC[x] <: Iterable[x]](expected: Int): ValueValidator[IterableOps[A, CC, CC[A]]] =
    CommonValidators.maximalLength(expected)

  def maximalLengthList[A](expected: Int): ValueValidator[List[A]]     = maximalLengthCollection[A, List](expected)
  def maximalLengthSeq[A](expected: Int): ValueValidator[Seq[A]]       = maximalLengthCollection[A, Seq](expected)
  def maximalLengthSet[A](expected: Int): ValueValidator[Set[A]]       = maximalLengthCollection[A, Set](expected)
  def maximalLengthVector[A](expected: Int): ValueValidator[Vector[A]] = maximalLengthCollection[A, Vector](expected)

  def exactLengthCollection[A, CC[x] <: Iterable[x]](expected: Int): ValueValidator[IterableOps[A, CC, CC[A]]] =
    CommonValidators.exactLength(expected)

  def exactLengthList[A](expected: Int): ValueValidator[List[A]]     = exactLengthCollection[A, List](expected)
  def exactLengthSeq[A](expected: Int): ValueValidator[Seq[A]]       = exactLengthCollection[A, Seq](expected)
  def exactLengthSet[A](expected: Int): ValueValidator[Set[A]]       = exactLengthCollection[A, Set](expected)
  def exactLengthVector[A](expected: Int): ValueValidator[Vector[A]] = exactLengthCollection[A, Vector](expected)
}

object CollectionValidators extends CollectionValidators
