package pl.muninn.simple.validation.validators

import cats.data.Validated

import pl.muninn.simple.validation.Validators

class OptionValidatorsSpec extends munit.FunSuite {

  test("forOpt should test only value when is defined, otherwise it should pass it as valid") {
    assert(Validators.forOpt(Validators.nonEmptyString).validate("forOpt", Some("")).isInvalid)
    assert(Validators.forOpt(Validators.nonEmptyString).validate("forOpt", Some("some")).isValid)
    assert(Validators.forOpt(Validators.nonEmptyString).validate("forOpt", None).isValid)
  }

  test("isDefined should fail if is not defined optional value") {
    assert(Validators.isDefined.validate("isDefined", None).isInvalid)
    assert(Validators.isDefined.validate("isDefined", Some("")).isValid)

    Validators.isDefined.validate("isDefined", None) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_field")
        assertEquals(errors.head.reason, "Non empty value required")
        assertEquals(errors.head.field, "isDefined")
    }
  }

  test("notDefined should fail if value is defined for optional value") {
    assert(Validators.notDefined.validate("notDefined", Some("")).isInvalid)
    assert(Validators.notDefined.validate("notDefined", None).isValid)

    Validators.notDefined.validate("notDefined", Some("")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_expected")
        assertEquals(errors.head.reason, "Value must be empty")
        assertEquals(errors.head.field, "notDefined")
    }
  }
}
