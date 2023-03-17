package pl.muninn.simple.validation.validators

import pl.muninn.simple.validation.ValueValidator
import pl.muninn.simple.validation.all._
import pl.muninn.simple.validation.test.{OptionalTestClass, PairTestClass, TypeTestClass, ValueType}

class CustomValidatorsSpec extends munit.FunSuite {
  trait Suit {
    val notEmptyValidator: ValueValidator[ValueType] = StringValidators.notEmptyString.contramap[ValueType](_.value)
  }
}
