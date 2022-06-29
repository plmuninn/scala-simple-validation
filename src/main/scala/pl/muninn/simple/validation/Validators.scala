package pl.muninn.simple.validation

import pl.muninn.simple.validation.validators._

trait Validators extends AnyTypeValidators with StringValidators with OptionValidators with CollectionValidators with NumberValidators

object Validators extends Validators
