package pl.muninn.simple.validation.validators

import cats.data.Validated

class OptionValidatorsSpec extends munit.FunSuite {

  test("ifDefined should test only value when is defined, otherwise it should pass it as valid") {
    assert(OptionValidators.ifDefined(StringValidators.notEmptyString).validate("ifDefined", Some("")).isInvalid)
    assert(OptionValidators.ifDefined(StringValidators.notEmptyString).validate("ifDefined", Some("some")).isValid)
    assert(OptionValidators.ifDefined(StringValidators.notEmptyString).validate("ifDefined", None).isValid)
  }

  test("defined should fail if is not defined optional value") {
    assert(OptionValidators.defined[String].validate("defined", None).isInvalid)
    assert(OptionValidators.defined[String].validate("defined", Some("")).isValid)

    OptionValidators.defined[Option[String]].validate("defined", None) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_field")
        assertEquals(errors.head.reason, "Non empty value required")
        assertEquals(errors.head.field, "defined")
    }
  }

  test("empty should fail if value is defined for optional value") {
    assert(OptionValidators.notDefined[String].validate("notDefined", Some("")).isInvalid)
    assert(OptionValidators.notDefined[String].validate("notDefined", None).isValid)

    OptionValidators.notDefined[String].validate("notDefined", Some("")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_expected")
        assertEquals(errors.head.reason, "Value must be empty")
        assertEquals(errors.head.field, "notDefined")
    }
  }
}
