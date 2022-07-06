package pl.muninn.simple.validation.validators

import cats.data.NonEmptyList

import pl.muninn.simple.validation.ValueValidator.{invalid, valid}
import pl.muninn.simple.validation.validators.CommonValidators.{EmptyMagnetic, LengthMagnetic}
import pl.muninn.simple.validation.{InvalidField, ValueValidator}

trait StringValidators {

  private implicit val stringEmptyMagnetic: EmptyMagnetic[String] = { value =>
    val blankSize = value.filter(Character.isWhitespace)

    value.length == blankSize.length
  }

  private implicit val stringLengthMagnetic: LengthMagnetic[String] = _.length

  def emptyString: ValueValidator[String] = CommonValidators.empty

  def notEmptyString: ValueValidator[String] = CommonValidators.notEmpty

  def minimalLengthString(expected: Int): ValueValidator[String] = CommonValidators.minimalLength(expected)

  def maximalLengthString(expected: Int): ValueValidator[String] = CommonValidators.maximalLength(expected)

  def exactLengthString(expected: Int): ValueValidator[String] = CommonValidators.exactLength(expected)

  private val emailRegex =
    """^[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$""".r

  val email: ValueValidator[String] = ValueValidator.instance { (key, value) =>
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
      minimalLengthOf: Int = 8,
      minCountOfSymbols: Int = 1,
      minCountOfDigits: Int = 1,
      minCountOfLowerCases: Int = 1,
      minCountOfUpperCases: Int = 1,
      symbolsList: List[Char] = DEFAULT_SYMBOL_LIST
  ): NonEmptyList[ValueValidator[String]] =
    minimalLengthString(minimalLengthOf) and
      minimalCountSymbols(minCountOfSymbols, symbolsList) and
      minimalCountDigits(minCountOfDigits) and
      minimalCountLowerCases(minCountOfLowerCases) and
      minimalCountUpperCases(minCountOfUpperCases)

}

object StringValidators extends StringValidators
