package pl.muninn.simple.validation.validators

import cats.data.Validated

import pl.muninn.simple.validation.Validators

class NumberValidatorsSpec extends munit.FunSuite {

  test("minimalNumberValue should fail when value is below minimal value") {
    assert(Validators.minimalNumberValue(10).validate("minimalNumberValue", 10).isValid)
    assert(Validators.minimalNumberValue(10).validate("minimalNumberValue", 11).isValid)
    assert(Validators.minimalNumberValue(10).validate("minimalNumberValue", 9).isInvalid)

    Validators.minimalNumberValue(10).validate("minimalNumberValue", 9) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "minimal_value")
        assertEquals(errors.head.reason, "Value must be greater or equal 10. Got 9")
        assertEquals(errors.head.field, "minimalNumberValue")
    }
  }

  test("maximalNumberValue should fail when value is above maximum value") {
    assert(Validators.maximalNumberValue(10).validate("maximalNumberValue", 10).isValid)
    assert(Validators.maximalNumberValue(10).validate("maximalNumberValue", 11).isInvalid)
    assert(Validators.maximalNumberValue(10).validate("maximalNumberValue", 9).isValid)

    Validators.maximalNumberValue(10).validate("maximalNumberValue", 11) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "maximal_value")
        assertEquals(errors.head.reason, "Value must be lower or equal 10. Got 11")
        assertEquals(errors.head.field, "maximalNumberValue")
    }
  }

  test("numberEqual should fail when value is not equal expected ") {
    assert(Validators.numberEqual(10).validate("numberEqual", 10).isValid)
    assert(Validators.numberEqual(10).validate("numberEqual", 11).isInvalid)
    assert(Validators.numberEqual(10).validate("numberEqual", 9).isInvalid)

    Validators.numberEqual(10).validate("numberEqual", 11) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "expected_value")
        assertEquals(errors.head.reason, "Value must be equal 10. Got 11")
        assertEquals(errors.head.field, "numberEqual")
    }
  }
}
