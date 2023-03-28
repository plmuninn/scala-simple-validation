package pl.muninn.simple.validation

import pl.muninn.simple.validation.validator.magnetic.{
  AnyTypeImplicits,
  CollectionImplicits,
  MapImplicits,
  NumberImplicits,
  OptionImplicits,
  StringImplicits
}

trait MagneticValidators
    extends AnyTypeImplicits
    with CollectionImplicits
    with NumberImplicits
    with OptionImplicits
    with StringImplicits
    with MapImplicits

object MagneticValidators extends MagneticValidators
