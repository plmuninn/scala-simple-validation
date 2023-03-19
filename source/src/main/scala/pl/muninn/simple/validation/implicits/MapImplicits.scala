package pl.muninn.simple.validation.implicits

import pl.muninn.simple.validation.model.{Validation, ValidationWithValidators}
import pl.muninn.simple.validation.validators.MapValidators

trait MapImplicits {

  implicit class MapValidation[K, V](field: Validation[Map[K, V]]) {
    def containsKey(key: K): ValidationWithValidators[Map[K, V]]             = field.is(MapValidators.containsKey(key))
    def containsKeys(keys: Iterable[K]): ValidationWithValidators[Map[K, V]] = field.is(MapValidators.containsKeys(keys))
    def empty: ValidationWithValidators[Map[K, V]]                         = field.is(MapValidators.empty[K, V])
    def notEmpty: ValidationWithValidators[Map[K, V]]                      = field.is(MapValidators.notEmpty[K, V])
    def minimalLength(expected: Int): ValidationWithValidators[Map[K, V]]  = field.is(MapValidators.minimalLength[K, V](expected))
    def maximumLength(expected: Int): ValidationWithValidators[Map[K, V]]  = field.is(MapValidators.maximalLength[K, V](expected))
    def expectedLength(expected: Int): ValidationWithValidators[Map[K, V]] = field.is(MapValidators.exactLength[K, V](expected))
  }

}

object MapImplicits extends MapImplicits
