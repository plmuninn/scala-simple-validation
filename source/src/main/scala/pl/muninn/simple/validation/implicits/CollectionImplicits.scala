package pl.muninn.simple.validation.implicits

import scala.collection.IterableOps

import cats.data.NonEmptyList

import pl.muninn.simple.validation.ValueValidator
import pl.muninn.simple.validation.model.{Validation, ValidationWithValidators}
import pl.muninn.simple.validation.validators.CollectionValidators

trait CollectionImplicits {

  implicit class CollectionValidation[A, CC[x] <: Iterable[x]](field: Validation[IterableOps[A, CC, CC[A]]]) {

    type CollectionType = IterableOps[A, CC, CC[A]]

    def all(fieldValidators: NonEmptyList[ValueValidator[A]]): ValidationWithValidators[CollectionType] =
      field.is(CollectionValidators.all[A, CC](fieldValidators))

    def empty: ValidationWithValidators[CollectionType] = field.is(CollectionValidators.emptyCollection[A, CC])

    def notEmpty: ValidationWithValidators[CollectionType] = field.is(CollectionValidators.notEmptyCollection[A, CC])

    def minimalLength(expected: Int): ValidationWithValidators[CollectionType] =
      field.is(CollectionValidators.minimalLengthCollection[A, CC](expected))

    def maximumLength(expected: Int): ValidationWithValidators[CollectionType] =
      field.is(CollectionValidators.minimalLengthCollection[A, CC](expected))

    def expectedLength(expected: Int): ValidationWithValidators[CollectionType] =
      field.is(CollectionValidators.exactLengthCollection[A, CC](expected))
  }

  implicit class ListValidation[A](field: Validation[List[A]])
      extends CollectionValidation[A, List](field.asInstanceOf[Validation[IterableOps[A, List, List[A]]]])

  implicit class SetValidation[A](field: Validation[Set[A]])
      extends CollectionValidation[A, Set](field.asInstanceOf[Validation[IterableOps[A, Set, Set[A]]]])

  implicit class SeqValidation[A](field: Validation[Seq[A]])
      extends CollectionValidation[A, Seq](field.asInstanceOf[Validation[IterableOps[A, Seq, Seq[A]]]])

  implicit class VectorValidation[A](field: Validation[Vector[A]])
      extends CollectionValidation[A, Vector](field.asInstanceOf[Validation[IterableOps[A, Vector, Vector[A]]]])
}

object CollectionImplicits extends CollectionImplicits
