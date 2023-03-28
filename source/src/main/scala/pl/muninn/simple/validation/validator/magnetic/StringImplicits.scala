package pl.muninn.simple.validation.validator.magnetic

import pl.muninn.simple.validation.model.{Field, FieldValidator}
import pl.muninn.simple.validation.validator.typed.StringValidators

trait StringImplicits {

  implicit class StringValidation(validation: Field[String]) {

    def empty: FieldValidator[String]    = validation.is(StringValidators.emptyString)
    def notEmpty: FieldValidator[String] = validation.is(StringValidators.notEmptyString)
    def email: FieldValidator[String]    = validation.is(StringValidators.email)
    def password: FieldValidator[String] = validation.is(StringValidators.password())

    def password(
        minimalLengthOf: Int = 8,
        minCountOfSymbols: Int = 1,
        minCountOfDigits: Int = 1,
        minCountOfLowerCases: Int = 1,
        minCountOfUpperCases: Int = 1,
        symbolsList: List[Char] = StringValidators.DEFAULT_SYMBOL_LIST
    ): FieldValidator[String] =
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

    def minimalLength(expected: Int): FieldValidator[String] =
      validation.is(StringValidators.minimalLengthString(expected))

    def maximalLength(expected: Int): FieldValidator[String] =
      validation.is(StringValidators.maximalLengthString(expected))

    def exactLength(expected: Int): FieldValidator[String] = validation.is(StringValidators.exactLengthString(expected))

    def containsSymbols(count: Int, symbols: List[Char] = StringValidators.DEFAULT_SYMBOL_LIST): FieldValidator[String] =
      validation.is(StringValidators.minimalCountSymbols(count, symbols))

    def containsDigits(count: Int): FieldValidator[String]    = validation.is(StringValidators.minimalCountDigits(count))
    def containsLowerCase(count: Int): FieldValidator[String] = validation.is(StringValidators.minimalCountLowerCases(count))
    def containsUpperCase(count: Int): FieldValidator[String] = validation.is(StringValidators.minimalCountUpperCases(count))
    def contains(expected: String): FieldValidator[String]    = validation.is(StringValidators.contains(expected))

    def containsAtLeastOne(expected: Iterable[String]): FieldValidator[String] =
      validation.is(StringValidators.containsAtLeastOne(expected))

    def equalAtLeastOne(expected: Iterable[String]): FieldValidator[String] = validation.is(StringValidators.equalAtLeastOne(expected))
  }

}

object StringImplicits extends StringImplicits
