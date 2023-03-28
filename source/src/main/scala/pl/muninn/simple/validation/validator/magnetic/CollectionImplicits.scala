package pl.muninn.simple.validation.validator.magnetic

import scala.collection.IterableOps

import cats.data.NonEmptyList

import pl.muninn.simple.validation.model.{Field, FieldValidator}
import pl.muninn.simple.validation.validator.ValueValidator
import pl.muninn.simple.validation.validator.typed.CollectionValidators

trait CollectionImplicits {

  implicit class CollectionValidation[A, CC[x] <: Iterable[x]](field: Field[IterableOps[A, CC, CC[A]]]) {

    type CollectionType = IterableOps[A, CC, CC[A]]

    def all(fieldValidators: NonEmptyList[ValueValidator[A]]): FieldValidator[CollectionType] =
      field.is(CollectionValidators.all[A, CC](fieldValidators))

    def empty: FieldValidator[CollectionType] = field.is(CollectionValidators.emptyCollection[A, CC])

    def notEmpty: FieldValidator[CollectionType] = field.is(CollectionValidators.notEmptyCollection[A, CC])

    def minimalLength(expected: Int): FieldValidator[CollectionType] =
      field.is(CollectionValidators.minimalLengthCollection[A, CC](expected))

    def maximumLength(expected: Int): FieldValidator[CollectionType] =
      field.is(CollectionValidators.minimalLengthCollection[A, CC](expected))

    def expectedLength(expected: Int): FieldValidator[CollectionType] =
      field.is(CollectionValidators.exactLengthCollection[A, CC](expected))
  }

  implicit class ListValidation[A](field: Field[List[A]])
      extends CollectionValidation[A, List](field.asInstanceOf[Field[IterableOps[A, List, List[A]]]])

  implicit class SetValidation[A](field: Field[Set[A]]) extends CollectionValidation[A, Set](field.asInstanceOf[Field[IterableOps[A, Set, Set[A]]]])

  implicit class SeqValidation[A](field: Field[Seq[A]]) extends CollectionValidation[A, Seq](field.asInstanceOf[Field[IterableOps[A, Seq, Seq[A]]]])

  implicit class VectorValidation[A](field: Field[Vector[A]])
      extends CollectionValidation[A, Vector](field.asInstanceOf[Field[IterableOps[A, Vector, Vector[A]]]])
}

object CollectionImplicits extends CollectionImplicits
