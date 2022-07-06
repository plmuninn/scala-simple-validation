package pl.muninn.simple.validation.validators

import cats.data.Validated

class AnyTypeValidatorsSpec extends munit.FunSuite {

  test("equal should fail if values are not equal") {
    assert(AnyTypeValidators.equalValue("test").validate("equalValue", "test").isValid)
    assert(AnyTypeValidators.equalValue("test").validate("equalValue", "").isInvalid)
    assert(AnyTypeValidators.equalValue(1).validate("equal", 1).isValid)
    assert(AnyTypeValidators.equalValue(1).validate("equal", 2).isInvalid)

    AnyTypeValidators.equalValue("test").validate("equalValue", "t") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "equal_field")
        assertEquals(errors.head.reason, "Value must equal test. Got t")
        assertEquals(errors.head.field, "equalValue")
    }
  }

  test("customValid should fail if testing function returns false") {
    assert(AnyTypeValidators.customValid[String]("testCode", _ => "Is not empty")(_.isEmpty).validate("customValid", "test").isInvalid)
    assert(AnyTypeValidators.customValid[String]("testCode", _ => "Is not empty")(_.isEmpty).validate("customValid", "").isValid)

    AnyTypeValidators.customValid[String]("testCode", _ => "Is not empty")(_.isEmpty).validate("customValid", "test") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "testCode")
        assertEquals(errors.head.reason, "Is not empty")
        assertEquals(errors.head.field, "customValid")
    }
  }

  test("fieldsEqual should fail if fields are not equal") {
    assert(AnyTypeValidators.fieldsEqual.validate("fieldsEqual", ("password", "pass")).isInvalid)
    assert(AnyTypeValidators.fieldsEqual.validate("fieldsEqual", ("password", "password")).isValid)

    AnyTypeValidators.fieldsEqual.validate("fieldsEqual", ("password", "")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "fields_not_equal")
        assertEquals(errors.head.reason, "Values fieldsEqual are not equal")
        assertEquals(errors.head.field, "fieldsEqual")
    }
  }
}
