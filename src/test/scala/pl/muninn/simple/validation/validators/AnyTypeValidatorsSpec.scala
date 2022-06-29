package pl.muninn.simple.validation.validators

import cats.data.Validated

import pl.muninn.simple.validation.Validators

class AnyTypeValidatorsSpec extends munit.FunSuite {

  test("equalValue should fail if values are not equal") {
    assert(Validators.equalValue("test").validate("equalValue", "test").isValid)
    assert(Validators.equalValue("test").validate("equalValue", "").isInvalid)
    assert(Validators.equalValue(1).validate("equalValue", 1).isValid)
    assert(Validators.equalValue(1).validate("equalValue", 2).isInvalid)

    Validators.equalValue("test").validate("equalValue", "t") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "equal_field")
        assertEquals(errors.head.reason, "Value not equal test. Got t")
        assertEquals(errors.head.field, "equalValue")
    }
  }

  test("customValid should fail if testing function returns false") {
    assert(Validators.customValid[String]("testCode", _ => "Is not empty")(_.isEmpty).validate("customValid", "test").isInvalid)
    assert(Validators.customValid[String]("testCode", _ => "Is not empty")(_.isEmpty).validate("customValid", "").isValid)

    Validators.customValid[String]("testCode", _ => "Is not empty")(_.isEmpty).validate("customValid", "test") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "testCode")
        assertEquals(errors.head.reason, "Is not empty")
        assertEquals(errors.head.field, "customValid")
    }
  }

  test("fieldsEqual should fail if fields are not equal") {
    assert(Validators.fieldsEqual.validate("fieldsEqual", ("password", "pass")).isInvalid)
    assert(Validators.fieldsEqual.validate("fieldsEqual", ("password", "password")).isValid)

    Validators.fieldsEqual.validate("fieldsEqual", ("password", "")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "fields_not_equal")
        assertEquals(errors.head.reason, "Values fieldsEqual are not equal")
        assertEquals(errors.head.field, "fieldsEqual")
    }
  }
}
