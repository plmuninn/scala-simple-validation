package pl.muninn.simple.validation.implicits

import pl.muninn.simple.validation.model.{Validation, ValidationWithValidators}
import pl.muninn.simple.validation.validators.StringValidators
import pl.muninn.simple.validation.validators.StringValidators._

trait StringImplicits {

  implicit class StringValidation(validation: Validation[String]) {
    def emptyString: ValidationWithValidators[String]     = validation.is(StringValidators.emptyString)
    def noneEmptyString: ValidationWithValidators[String] = validation.is(StringValidators.noneEmptyString)
    def emailString: ValidationWithValidators[String]     = validation.is(StringValidators.emailString)
    def password: ValidationWithValidators[String]        = validation.is(StringValidators.password())

    def password(
        minimalLength: Int = 8,
        minCountOfSymbols: Int = 1,
        minCountOfDigits: Int = 1,
        minCountOfLowerCases: Int = 1,
        minCountOfUpperCases: Int = 1,
        symbolsList: List[Char] = DEFAULT_SYMBOL_LIST
    ): ValidationWithValidators[String] =
      validation.is(
        StringValidators
          .password(
            minimalLength = minimalLength,
            minCountOfSymbols = minCountOfSymbols,
            minCountOfDigits = minCountOfDigits,
            minCountOfLowerCases = minCountOfLowerCases,
            minCountOfUpperCases = minCountOfUpperCases,
            symbolsList = symbolsList
          )
      )

    def minimalLength(expected: Int): ValidationWithValidators[String]  = validation.is(stringMinimalLength(expected))
    def maximalLength(expected: Int): ValidationWithValidators[String]  = validation.is(stringMaximalLength(expected))
    def expectedLength(expected: Int): ValidationWithValidators[String] = validation.is(stringLength(expected))

    def containsSymbols(count: Int, symbols: List[Char] = DEFAULT_SYMBOL_LIST): ValidationWithValidators[String] =
      validation.is(minimalCountSymbols(count, symbols))

    def containsDigits(count: Int): ValidationWithValidators[String]    = validation.is(minimalCountDigits(count))
    def containsLowerCase(count: Int): ValidationWithValidators[String] = validation.is(minimalCountLowerCases(count))
    def containsUpperCase(count: Int): ValidationWithValidators[String] = validation.is(minimalCountUpperCases(count))
  }

}

object StringImplicits extends StringImplicits
