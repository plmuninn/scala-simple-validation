package pl.muninn.simple.validation.validators

import pl.muninn.simple.validation.ValueValidator.{invalid, valid}
import pl.muninn.simple.validation.validators.CommonValidators.{EmptyMagnetic, LengthMagnetic}
import pl.muninn.simple.validation.{InvalidField, ValueValidator}

trait MapValidators {

  private implicit def mapEmptyMagnetic[K, V]: EmptyMagnetic[Map[K, V]]   = _.isEmpty
  private implicit def mapLengthMagnetic[K, V]: LengthMagnetic[Map[K, V]] = _.size

  def containsKey[K, V](expected: K): ValueValidator[Map[K, V]] = ValueValidator.instance { (key, value) =>
    if (value.keys.exists(_ == expected)) valid else invalid(InvalidField.KeyMissing(key, expected))
  }

  def containsKeys[K, V](keys: Iterable[K]): ValueValidator[Map[K, V]] = ValueValidator.instance { (key, value) =>
    val valueKeys   = value.keys
    val missingKeys = keys.filterNot(k => valueKeys.exists(_ == k))

    if (missingKeys.isEmpty) valid
    else {
      if (missingKeys.size == 1) {
        invalid(InvalidField.KeyMissing(key, missingKeys.head))
      } else {
        invalid(InvalidField.KeysMissing(key, missingKeys))
      }
    }
  }

  def notEmpty[K, V]: ValueValidator[Map[K, V]]                     = CommonValidators.notEmpty
  def empty[K, V]: ValueValidator[Map[K, V]]                        = CommonValidators.empty
  def maximalLength[K, V](expected: Int): ValueValidator[Map[K, V]] = CommonValidators.maximalLength(expected)
  def minimalLength[K, V](expected: Int): ValueValidator[Map[K, V]] = CommonValidators.minimalLength(expected)
  def exactLength[K, V](expected: Int): ValueValidator[Map[K, V]]   = CommonValidators.exactLength(expected)
}

object MapValidators extends MapValidators
