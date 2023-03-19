package pl.muninn.simple.validation.implicits

import pl.muninn.simple.validation.model.{Validation, ValidationWithValidators}
import pl.muninn.simple.validation.validators.StringValidators

trait StringImplicits {

  implicit class StringValidation(validation: Validation[String]) {

    def empty: ValidationWithValidators[String]    = validation.is(StringValidators.emptyString)
    def notEmpty: ValidationWithValidators[String] = validation.is(StringValidators.notEmptyString)
    def email: ValidationWithValidators[String]    = validation.is(StringValidators.email)
    def password: ValidationWithValidators[String] = validation.is(StringValidators.password())

    def password(
        minimalLengthOf: Int = 8,
        minCountOfSymbols: Int = 1,
        minCountOfDigits: Int = 1,
        minCountOfLowerCases: Int = 1,
        minCountOfUpperCases: Int = 1,
        symbolsList: List[Char] = StringValidators.DEFAULT_SYMBOL_LIST
    ): ValidationWithValidators[String] =
      validation.is(
        StringValidators
          .password(
            minimalLengthOf = minimalLengthOf,
            minCountOfSymbols = minCountOfSymbols,
            minCountOfDigits = minCountOfDigits,
            minCountOfLowerCases = minCountOfLowerCases,
            minCountOfUpperCases = minCountOfUpperCases,
            symbolsList = symbolsList
          )
      )

    def minimalLength(expected: Int): ValidationWithValidators[String] =
      validation.is(StringValidators.minimalLengthString(expected))

    def maximalLength(expected: Int): ValidationWithValidators[String] =
      validation.is(StringValidators.maximalLengthString(expected))

    def exactLength(expected: Int): ValidationWithValidators[String] = validation.is(StringValidators.exactLengthString(expected))

    def containsSymbols(count: Int, symbols: List[Char] = StringValidators.DEFAULT_SYMBOL_LIST): ValidationWithValidators[String] =
      validation.is(StringValidators.minimalCountSymbols(count, symbols))

    def containsDigits(count: Int): ValidationWithValidators[String]    = validation.is(StringValidators.minimalCountDigits(count))
    def containsLowerCase(count: Int): ValidationWithValidators[String] = validation.is(StringValidators.minimalCountLowerCases(count))
    def containsUpperCase(count: Int): ValidationWithValidators[String] = validation.is(StringValidators.minimalCountUpperCases(count))
    def contains(expected: String): ValidationWithValidators[String]    = validation.is(StringValidators.contains(expected))

    def containsAtLeastOne(expected: Iterable[String]): ValidationWithValidators[String] =
      validation.is(StringValidators.containsAtLeastOne(expected))

    def equalAtLeastOne(expected: Iterable[String]): ValidationWithValidators[String] = validation.is(StringValidators.equalAtLeastOne(expected))
  }

}

object StringImplicits extends StringImplicits
