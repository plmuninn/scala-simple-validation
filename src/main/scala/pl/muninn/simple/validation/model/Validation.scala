package pl.muninn.simple.validation.model

import scala.collection.IterableOps

import cats.data.NonEmptyList

import pl.muninn.simple.validation.CommonValidators._
import pl.muninn.simple.validation.{Implicits, ValueValidator}

class Validation[T](val key: String, val value: T) {
  def is(validators: NonEmptyList[ValueValidator[T]]): ValidationWithValidators[T] = new ValidationWithValidators[T](this, validators)
  def is(validator: ValueValidator[T]): ValidationWithValidators[T]                = new ValidationWithValidators[T](this, NonEmptyList.of(validator))
}

object Validation extends Implicits {

  implicit class AnyValidation[T](validation: Validation[T]) {
    def equal(expected: T): ValidationWithValidators[T]                                    = validation.is(equalValue(expected))
    def custom(code: String, reason: String)(f: T => Boolean): ValidationWithValidators[T] = validation.is(customValid(code, { _: T => reason })(f))
    def custom(code: String, reason: T => String)(f: T => Boolean): ValidationWithValidators[T] = validation.is(customValid(code, reason)(f))
  }

  implicit class StringValidation(validation: Validation[String]) {
    def isNonEmptyString: ValidationWithValidators[String]              = validation.is(nonEmptyString)
    def isEmail: ValidationWithValidators[String]                       = validation.is(emailString)
    def isComplexPassword: ValidationWithValidators[String]             = validation.is(complexPassword)
    def minimalLength(expected: Int): ValidationWithValidators[String]  = validation.is(stringMinimalLength(expected))
    def maximalLength(expected: Int): ValidationWithValidators[String]  = validation.is(stringMaximalLength(expected))
    def expectedLength(expected: Int): ValidationWithValidators[String] = validation.is(stringLength(expected))
  }

  implicit class PairValidation[T](field: Validation[(T, T)]) {
    def equalValues: ValidationWithValidators[(T, T)] = field.is(fieldsEqual[T])
  }

  implicit class NumericValidation[T](field: Validation[T])(implicit numeric: Numeric[T]) {
    def min(value: T): ValidationWithValidators[T]   = field.is(minimalNumberValue(value))
    def max(value: T): ValidationWithValidators[T]   = field.is(maximalNumberValue(value))
    def equal(value: T): ValidationWithValidators[T] = field.is(numberEqual(value))
  }

  implicit class OptionValidation[T](field: Validation[Option[T]]) {
    def ifDefined(fieldValidator: NonEmptyList[ValueValidator[T]]): ValidationWithValidators[Option[T]]  = field.is(forOpt[T](fieldValidator))
    def definedAnd(fieldValidator: NonEmptyList[ValueValidator[T]]): ValidationWithValidators[Option[T]] = defined.and(forOpt[T](fieldValidator))
    def defined: ValidationWithValidators[Option[T]]                                                     = field.is(isDefined[T])
    def undefined: ValidationWithValidators[Option[T]]                                                   = field.is(notDefined[T])
  }

  implicit class CollectionValidation[A, CC[x] <: Iterable[x]](field: Validation[IterableOps[A, CC, CC[A]]]) {

    type CollectionType = IterableOps[A, CC, CC[A]]

    def every(fieldValidators: NonEmptyList[ValueValidator[A]]): ValidationWithValidators[CollectionType] = field.is(all[A, CC](fieldValidators))
    def nonEmpty: ValidationWithValidators[CollectionType]                                                = field.is(noneEmptyCollection[A, CC])
    def empty: ValidationWithValidators[CollectionType]                                                   = field.is(emptyCollection[A, CC])
    def minimalLength(expected: Int): ValidationWithValidators[CollectionType]  = field.is(collectionMinimalLength[A, CC](expected))
    def maximumLength(expected: Int): ValidationWithValidators[CollectionType]  = field.is(collectionMaximalLength[A, CC](expected))
    def expectedLength(expected: Int): ValidationWithValidators[CollectionType] = field.is(collectionLength[A, CC](expected))
  }
}
