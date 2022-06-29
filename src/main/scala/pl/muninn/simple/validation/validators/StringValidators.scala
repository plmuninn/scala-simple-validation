package pl.muninn.simple.validation.validators

import pl.muninn.simple.validation.ValueValidator.{invalid, valid}
import pl.muninn.simple.validation.{InvalidField, ValueValidator}

trait StringValidators {
  val emptyString: ValueValidator[String] = ValueValidator.instance { (key, value) =>
    if (value.isBlank) valid else invalid(InvalidField.ExpectedEmpty(key))
  }

  val nonEmptyString: ValueValidator[String] = ValueValidator.instance { (key, value) =>
    if (value.isBlank) invalid(InvalidField.EmptyField(key)) else valid
  }

  private val emailRegex =
    """^[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$""".r

  val emailString: ValueValidator[String] = ValueValidator.instance { (key, value) =>
    if (!emailRegex.matches(value)) invalid(InvalidField.NotEmail(key)) else valid
  }

  private val complexPasswordRegex =
    """^(?:(?=.*[a-z])(?:(?=.*[A-Z])(?=.*[\d\W])|(?=.*\W)(?=.*\d))|(?=.*\W)(?=.*[A-Z])(?=.*\d)).{8,}$""".r

  val complexPassword: ValueValidator[String] = ValueValidator.instance { (key, value) =>
    if (!complexPasswordRegex.matches(value)) invalid(InvalidField.NotComplexPassword(key)) else valid
  }

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
