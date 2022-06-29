package pl.muninn.simple.validation.implicits

import pl.muninn.simple.validation.model.{Validation, ValidationWithValidators}
import pl.muninn.simple.validation.validators.MapValidators

trait MapImplicits {

  implicit class MapValidation[K, V](field: Validation[Map[K, V]]) {
    def containsKey(key: K): ValidationWithValidators[Map[K, V]]             = field.is(MapValidators.containsKey(key))
    def containsKeys(keys: Iterable[K]): ValidationWithValidators[Map[K, V]] = field.is(MapValidators.containsKeys(keys))
  }

}

object MapImplicits extends MapImplicits
