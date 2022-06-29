package pl.muninn.simple.validation.validators

import pl.muninn.simple.validation.ValueValidator.{invalid, valid}
import pl.muninn.simple.validation.{InvalidField, ValueValidator}

trait MapValidators {

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
}

object MapValidators extends MapValidators
