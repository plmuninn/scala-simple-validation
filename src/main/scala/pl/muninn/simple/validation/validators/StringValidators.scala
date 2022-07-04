package pl.muninn.simple.validation.validators

import cats.data.NonEmptyList

import pl.muninn.simple.validation.ValueValidator.{invalid, valid}
import pl.muninn.simple.validation.{InvalidField, ValueValidator}

trait StringValidators {
  val emptyString: ValueValidator[String] = ValueValidator.instance { (key, value) =>
    if (value.isBlank) valid else invalid(InvalidField.ExpectedEmpty(key))
  }

  val noneEmptyString: ValueValidator[String] = ValueValidator.instance { (key, value) =>
    if (value.isBlank) invalid(InvalidField.EmptyField(key)) else valid
  }

  private val emailRegex =
    """^[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$""".r

  val emailString: ValueValidator[String] = ValueValidator.instance { (key, value) =>
    if (!emailRegex.matches(value)) invalid(InvalidField.NotEmail(key)) else valid
  }

  // format: off
  val DEFAULT_SYMBOL_LIST: List[Char] = List(
    '@', '$', '#', '!', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+',
    ',', '.', '/', '?', ';', '\'', '\\', '[', ']', '{', '}', ':', '\"', '|',
    '<', '>', '/', '~', '`', '§'
  )
  // format: on

  def minimalCountSymbols(minCountOfSymbols: Int, symbolsList: List[Char] = DEFAULT_SYMBOL_LIST): ValueValidator[String] =
    ValueValidator.instance { (key, value) =>
      val symbols = value.filter(symbolsList.contains)

      if (symbols.length >= minCountOfSymbols) valid else invalid(InvalidField.MinCountOfSymbols(key, minCountOfSymbols, symbols.length, symbolsList))
    }

  def minimalCountDigits(minCountOfDigits: Int): ValueValidator[String] = ValueValidator.instance { (key, value) =>
    val digits = value.filter(Character.isDigit)

    if (digits.length >= minCountOfDigits) valid else invalid(InvalidField.MinCountOfDigits(key, minCountOfDigits, digits.length))
  }

  def minimalCountLowerCases(minCountOfLowerCases: Int): ValueValidator[String] = ValueValidator.instance { (key, value) =>
    val lowerCases = value.filter(Character.isLowerCase)

    if (lowerCases.length >= minCountOfLowerCases) valid else invalid(InvalidField.MinCountOfLowerCases(key, minCountOfLowerCases, lowerCases.length))
  }

  def minimalCountUpperCases(minCountOfUpperCases: Int): ValueValidator[String] = ValueValidator.instance { (key, value) =>
    val upperCase = value.filter(Character.isUpperCase)

    if (upperCase.length >= minCountOfUpperCases) valid else invalid(InvalidField.MinCountOfUpperCases(key, minCountOfUpperCases, upperCase.length))
  }

  def password(
      minimalLength: Int = 8,
      minCountOfSymbols: Int = 1,
      minCountOfDigits: Int = 1,
      minCountOfLowerCases: Int = 1,
      minCountOfUpperCases: Int = 1,
      symbolsList: List[Char] = DEFAULT_SYMBOL_LIST
  ): NonEmptyList[ValueValidator[String]] =
    stringMinimalLength(minimalLength) and
      minimalCountSymbols(minCountOfSymbols, symbolsList) and
      minimalCountDigits(minCountOfDigits) and
      minimalCountLowerCases(minCountOfLowerCases) and
      minimalCountUpperCases(minCountOfUpperCases)

  def stringMinimalLength(expected: Int): ValueValidator[String] = ValueValidator.instance { (key, value) =>
    if (value.length >= expected) valid else invalid(InvalidField.MinimalLength(key, expected, value.length))
  }

  def stringMaximalLength(expected: Int): ValueValidator[String] = ValueValidator.instance { (key, value) =>
    if (value.length <= expected) valid else invalid(InvalidField.MaximalLength(key, expected, value.length))
  }

  def stringLength(expected: Int): ValueValidator[String] = ValueValidator.instance { (key, value) =>
    if (value.length == expected) valid else invalid(InvalidField.ExpectedLength(key, expected, value.length))
  }
}

object StringValidators extends StringValidators
