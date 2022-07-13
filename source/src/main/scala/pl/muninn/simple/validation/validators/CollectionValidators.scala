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

  def allInList[A](validator: ValueValidator[A]): ValueValidator[IterableOps[A, List, List[A]]]       = all(NonEmptyList.one(validator))
  def allInSeq[A](validator: ValueValidator[A]): ValueValidator[IterableOps[A, Seq, Seq[A]]]          = all(NonEmptyList.one(validator))
  def allInSet[A](validator: ValueValidator[A]): ValueValidator[IterableOps[A, Set, Set[A]]]          = all(NonEmptyList.one(validator))
  def allInVector[A](validator: ValueValidator[A]): ValueValidator[IterableOps[A, Vector, Vector[A]]] = all(NonEmptyList.one(validator))

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

  def allInList[A](validators: NonEmptyList[ValueValidator[A]]): ValueValidator[IterableOps[A, List, List[A]]]       = all(validators)
  def allInSeq[A](validators: NonEmptyList[ValueValidator[A]]): ValueValidator[IterableOps[A, Seq, Seq[A]]]          = all(validators)
  def allInSet[A](validators: NonEmptyList[ValueValidator[A]]): ValueValidator[IterableOps[A, Set, Set[A]]]          = all(validators)
  def allInVector[A](validators: NonEmptyList[ValueValidator[A]]): ValueValidator[IterableOps[A, Vector, Vector[A]]] = all(validators)

  private implicit def collectionEmptyMagnetic[A, CC[x] <: Iterable[x]]: EmptyMagnetic[IterableOps[A, CC, CC[A]]] = _.isEmpty

  private implicit def collectionLengthMagnetic[A, CC[x] <: Iterable[x]]: LengthMagnetic[IterableOps[A, CC, CC[A]]] = _.size

  def emptyCollection[A, CC[x] <: Iterable[x]]: ValueValidator[IterableOps[A, CC, CC[A]]] = CommonValidators.empty

  def emptyList[A]: ValueValidator[IterableOps[A, List, List[A]]] = CommonValidators.empty

  def emptySeq[A]: ValueValidator[IterableOps[A, Seq, Seq[A]]] = CommonValidators.empty

  def emptySet[A]: ValueValidator[IterableOps[A, Set, Set[A]]] = CommonValidators.empty

  def emptyVector[A]: ValueValidator[IterableOps[A, Vector, Vector[A]]] = CommonValidators.empty

  def notEmptyCollection[A, CC[x] <: Iterable[x]]: ValueValidator[IterableOps[A, CC, CC[A]]] = CommonValidators.notEmpty

  def notEmptyList[A]: ValueValidator[IterableOps[A, List, List[A]]] = CommonValidators.notEmpty

  def notEmptySeq[A]: ValueValidator[IterableOps[A, Seq, Seq[A]]] = CommonValidators.notEmpty

  def notEmptySet[A]: ValueValidator[IterableOps[A, Set, Set[A]]] = CommonValidators.notEmpty

  def notEmptyVector[A]: ValueValidator[IterableOps[A, Vector, Vector[A]]] = CommonValidators.notEmpty

  def minimalLengthCollection[A, CC[x] <: Iterable[x]](expected: Int): ValueValidator[IterableOps[A, CC, CC[A]]] =
    CommonValidators.minimalLength(expected)

  def minimalLengthList[A](expected: Int): ValueValidator[IterableOps[A, List, List[A]]] = CommonValidators.minimalLength(expected)

  def minimalLengthSeq[A](expected: Int): ValueValidator[IterableOps[A, Seq, Seq[A]]] = CommonValidators.minimalLength(expected)

  def minimalLengthSet[A](expected: Int): ValueValidator[IterableOps[A, Set, Set[A]]] = CommonValidators.minimalLength(expected)

  def minimalLengthVector[A](expected: Int): ValueValidator[IterableOps[A, Vector, Vector[A]]] = CommonValidators.minimalLength(expected)

  def maximalLengthCollection[A, CC[x] <: Iterable[x]](expected: Int): ValueValidator[IterableOps[A, CC, CC[A]]] =
    CommonValidators.maximalLength(expected)

  def maximalLengthList[A](expected: Int): ValueValidator[IterableOps[A, List, List[A]]]       = CommonValidators.maximalLength(expected)
  def maximalLengthSeq[A](expected: Int): ValueValidator[IterableOps[A, Seq, Seq[A]]]          = CommonValidators.maximalLength(expected)
  def maximalLengthSet[A](expected: Int): ValueValidator[IterableOps[A, Set, Set[A]]]          = CommonValidators.maximalLength(expected)
  def maximalLengthVector[A](expected: Int): ValueValidator[IterableOps[A, Vector, Vector[A]]] = CommonValidators.maximalLength(expected)

  def exactLengthCollection[A, CC[x] <: Iterable[x]](expected: Int): ValueValidator[IterableOps[A, CC, CC[A]]] =
    CommonValidators.exactLength(expected)

  def exactLengthList[A](expected: Int): ValueValidator[IterableOps[A, List, List[A]]]       = CommonValidators.exactLength(expected)
  def exactLengthSeq[A](expected: Int): ValueValidator[IterableOps[A, Seq, Seq[A]]]          = CommonValidators.exactLength(expected)
  def exactLengthSet[A](expected: Int): ValueValidator[IterableOps[A, Set, Set[A]]]          = CommonValidators.exactLength(expected)
  def exactLengthVector[A](expected: Int): ValueValidator[IterableOps[A, Vector, Vector[A]]] = CommonValidators.exactLength(expected)
}

object CollectionValidators extends CollectionValidators
