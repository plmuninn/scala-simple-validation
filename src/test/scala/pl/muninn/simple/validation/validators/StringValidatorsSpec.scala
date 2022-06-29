package pl.muninn.simple.validation.validators

import cats.data.Validated

import pl.muninn.simple.validation.Validators

class StringValidatorsSpec extends munit.FunSuite {

  test("emptyString should fail if string is empty and pass if isn't") {
    assert(Validators.emptyString.validate("nonEmptyStringTest", "t").isInvalid)
    assert(Validators.emptyString.validate("nonEmptyStringTest", "").isValid)

    Validators.emptyString.validate("emptyString", "t") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_expected")
        assertEquals(errors.head.reason, "Value must be empty")
        assertEquals(errors.head.field, "emptyString")
    }
  }

  test("nonEmptyString should fail if string is empty and pass if isn't") {
    assert(Validators.nonEmptyString.validate("nonEmptyStringTest", "").isInvalid)
    assert(Validators.nonEmptyString.validate("nonEmptyStringTest", "t").isValid)

    Validators.nonEmptyString.validate("nonEmptyStringTest", "") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_field")
        assertEquals(errors.head.reason, "Non empty value required")
        assertEquals(errors.head.field, "nonEmptyStringTest")
    }
  }

  test("emailString should fail if string is not an email and pass if is") {
    assert(Validators.emailString.validate("emailTest", "some string").isInvalid)
    assert(Validators.emailString.validate("emailTest", "test@example.com").isValid)

    Validators.emailString.validate("emailTest", "") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "email_field")
        assertEquals(errors.head.reason, "Value must be a valid email")
        assertEquals(errors.head.field, "emailTest")
    }
  }

  test("complexPasswordRegex should fail if string is not an 8 symbols long with big and small letters, number and symbols and pass if is") {
    assert(Validators.complexPassword.validate("passwordTest", "password").isInvalid)
    assert(Validators.complexPassword.validate("passwordTest", "123Pasword.#").isValid)

    Validators.complexPassword.validate("passwordTest", "test") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "password_complexity")
        assertEquals(
          errors.head.reason,
          "Password needs to be at least 8 characters long and contains 1 number and 1 special symbol and big and small letters"
        )
        assertEquals(errors.head.field, "passwordTest")
    }
  }

  test("stringMinimalLength should fail string is shorter then minimal length") {
    assert(Validators.stringMinimalLength(2).validate("stringMinimalLength", "t").isInvalid)
    assert(Validators.stringMinimalLength(2).validate("stringMinimalLength", "test").isValid)
    assert(Validators.stringMinimalLength(2).validate("stringMinimalLength", "te").isValid)

    Validators.stringMinimalLength(2).validate("stringMinimalLength", "1") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "minimal_length")
        assertEquals(errors.head.reason, "Length must be greater or equal 2. Got 1")
        assertEquals(errors.head.field, "stringMinimalLength")
    }
  }

  test("stringMaximalLength should fail string is longer then maximal length") {
    assert(Validators.stringMaximalLength(3).validate("stringMaximalLength", "test").isInvalid)
    assert(Validators.stringMaximalLength(3).validate("stringMaximalLength", "tes").isValid)
    assert(Validators.stringMaximalLength(3).validate("stringMaximalLength", "").isValid)

    Validators.stringMaximalLength(3).validate("stringMaximalLength", "test") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "maximal_length")
        assertEquals(errors.head.reason, "Length must be lower or equal 3. Got 4")
        assertEquals(errors.head.field, "stringMaximalLength")
    }
  }

  test("stringLength should fail string is has different length then expected") {
    assert(Validators.stringLength(4).validate("stringLength", "tes").isInvalid)
    assert(Validators.stringLength(4).validate("stringLength", "test").isValid)
    assert(Validators.stringLength(4).validate("stringLength", "").isInvalid)

    Validators.stringLength(4).validate("stringLength", "tes") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "expected_length")
        assertEquals(errors.head.reason, "Length must be equal 4. Got 3")
        assertEquals(errors.head.field, "stringLength")
    }
  }
}
