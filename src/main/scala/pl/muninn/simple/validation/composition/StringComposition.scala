package pl.muninn.simple.validation.composition

import pl.muninn.simple.validation.model.{Validation, ValidationWithValidators}
import pl.muninn.simple.validation.validators.StringValidators.{
  complexPassword,
  emailString,
  nonEmptyString,
  stringLength,
  stringMaximalLength,
  stringMinimalLength
}

trait StringComposition {

  implicit class StringValidation(validation: Validation[String]) {
    def isNonEmptyString: ValidationWithValidators[String]              = validation.is(nonEmptyString)
    def isEmail: ValidationWithValidators[String]                       = validation.is(emailString)
    def isComplexPassword: ValidationWithValidators[String]             = validation.is(complexPassword)
    def minimalLength(expected: Int): ValidationWithValidators[String]  = validation.is(stringMinimalLength(expected))
    def maximalLength(expected: Int): ValidationWithValidators[String]  = validation.is(stringMaximalLength(expected))
    def expectedLength(expected: Int): ValidationWithValidators[String] = validation.is(stringLength(expected))
  }

}

object StringComposition extends StringComposition
