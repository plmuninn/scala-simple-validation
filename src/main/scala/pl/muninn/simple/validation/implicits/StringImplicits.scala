package pl.muninn.simple.validation.implicits

import pl.muninn.simple.validation.model.{Validation, ValidationWithValidators}
import pl.muninn.simple.validation.validators.StringValidators
import pl.muninn.simple.validation.validators.StringValidators.{stringLength, stringMaximalLength, stringMinimalLength}

trait StringImplicits {

  implicit class StringValidation(validation: Validation[String]) {
    def noneEmptyString: ValidationWithValidators[String]               = validation.is(StringValidators.noneEmptyString)
    def emailString: ValidationWithValidators[String]                   = validation.is(StringValidators.emailString)
    def complexPassword: ValidationWithValidators[String]               = validation.is(StringValidators.complexPassword)
    def minimalLength(expected: Int): ValidationWithValidators[String]  = validation.is(stringMinimalLength(expected))
    def maximalLength(expected: Int): ValidationWithValidators[String]  = validation.is(stringMaximalLength(expected))
    def expectedLength(expected: Int): ValidationWithValidators[String] = validation.is(stringLength(expected))
  }

}

object StringImplicits extends StringImplicits
