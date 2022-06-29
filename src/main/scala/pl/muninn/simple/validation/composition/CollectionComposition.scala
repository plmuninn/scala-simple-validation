package pl.muninn.simple.validation.composition

import scala.collection.IterableOps

import cats.data.NonEmptyList

import pl.muninn.simple.validation.ValueValidator
import pl.muninn.simple.validation.model.{Validation, ValidationWithValidators}
import pl.muninn.simple.validation.validators.CollectionValidators.{
  all,
  collectionLength,
  collectionMaximalLength,
  collectionMinimalLength,
  emptyCollection,
  noneEmptyCollection
}

trait CollectionComposition {

  implicit class CollectionValidation[A, CC[x] <: Iterable[x]](field: Validation[IterableOps[A, CC, CC[A]]]) {

    type CollectionType = IterableOps[A, CC, CC[A]]

    def every(fieldValidators: NonEmptyList[ValueValidator[A]]): ValidationWithValidators[CollectionType] = field.is(all[A, CC](fieldValidators))
    def nonEmpty: ValidationWithValidators[CollectionType]                                                = field.is(noneEmptyCollection[A, CC])
    def empty: ValidationWithValidators[CollectionType]                                                   = field.is(emptyCollection[A, CC])
    def minimalLength(expected: Int): ValidationWithValidators[CollectionType]  = field.is(collectionMinimalLength[A, CC](expected))
    def maximumLength(expected: Int): ValidationWithValidators[CollectionType]  = field.is(collectionMaximalLength[A, CC](expected))
    def expectedLength(expected: Int): ValidationWithValidators[CollectionType] = field.is(collectionLength[A, CC](expected))
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

object CollectionComposition extends CollectionComposition
