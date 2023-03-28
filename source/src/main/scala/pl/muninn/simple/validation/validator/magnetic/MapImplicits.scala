package pl.muninn.simple.validation.validator.magnetic

import pl.muninn.simple.validation.model.{Field, FieldValidator}
import pl.muninn.simple.validation.validator.typed.MapValidators

trait MapImplicits {

  implicit class MapValidation[K, V](field: Field[Map[K, V]]) {
    def containsKey(key: K): FieldValidator[Map[K, V]]             = field.is(MapValidators.containsKey(key))
    def containsKeys(keys: Iterable[K]): FieldValidator[Map[K, V]] = field.is(MapValidators.containsKeys(keys))
    def empty: FieldValidator[Map[K, V]]                           = field.is(MapValidators.empty[K, V])
    def notEmpty: FieldValidator[Map[K, V]]                        = field.is(MapValidators.notEmpty[K, V])
    def minimalLength(expected: Int): FieldValidator[Map[K, V]]    = field.is(MapValidators.minimalLength[K, V](expected))
    def maximumLength(expected: Int): FieldValidator[Map[K, V]]    = field.is(MapValidators.maximalLength[K, V](expected))
    def expectedLength(expected: Int): FieldValidator[Map[K, V]]   = field.is(MapValidators.exactLength[K, V](expected))
  }

}

object MapImplicits extends MapImplicits
