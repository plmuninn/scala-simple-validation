package pl.muninn.simple.validation.validators

import cats.data.Validated

class NumberValidatorsSpec extends munit.FunSuite {

  test("min should fail when value is below minimal value") {
    assert(NumberValidators.min(10).validate("min", 10).isValid)
    assert(NumberValidators.min(10).validate("min", 11).isValid)
    assert(NumberValidators.min(10).validate("min", 9).isInvalid)

    NumberValidators.min(10).validate("min", 9) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "minimal_value")
        assertEquals(errors.head.reason, "Value must be greater or equal 10. Got 9")
        assertEquals(errors.head.field, "min")
    }
  }

  test("max should fail when value is above maximum value") {
    assert(NumberValidators.max(10).validate("max", 10).isValid)
    assert(NumberValidators.max(10).validate("max", 11).isInvalid)
    assert(NumberValidators.max(10).validate("max", 9).isValid)

    NumberValidators.max(10).validate("max", 11) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "maximal_value")
        assertEquals(errors.head.reason, "Value must be lower or equal 10. Got 11")
        assertEquals(errors.head.field, "max")
    }
  }

  test("equalNumber should fail when value is not equal expected ") {
    assert(NumberValidators.equalNumber(10).validate("equalNumber", 10).isValid)
    assert(NumberValidators.equalNumber(10).validate("equalNumber", 11).isInvalid)
    assert(NumberValidators.equalNumber(10).validate("equalNumber", 9).isInvalid)

    NumberValidators.equalNumber(10).validate("equalNumber", 11) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "equal_field")
        assertEquals(errors.head.reason, "Value must equal 10. Got 11")
        assertEquals(errors.head.field, "equalNumber")
    }
  }
}
