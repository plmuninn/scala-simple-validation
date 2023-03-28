package pl.muninn.simple.validation

import pl.muninn.simple.validation.validator.typed.{
  AnyTypeValidators,
  CollectionValidators,
  MapValidators,
  NumberValidators,
  OptionValidators,
  StringValidators
}

trait TypedValidators
    extends AnyTypeValidators
    with StringValidators
    with OptionValidators
    with CollectionValidators
    with NumberValidators
    with MapValidators

object TypedValidators extends TypedValidators
