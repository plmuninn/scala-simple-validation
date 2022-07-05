package pl.muninn.simple.validation.validators

import cats.data.Validated

import pl.muninn.simple.validation.Validators
import pl.muninn.simple.validation.model.Validation

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

  test("noneEmptyString should fail if string is empty and pass if isn't") {
    assert(Validators.noneEmptyString.validate("noneEmptyString", "").isInvalid)
    assert(Validators.noneEmptyString.validate("noneEmptyString", "t").isValid)

    Validators.noneEmptyString.validate("noneEmptyString", "") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_field")
        assertEquals(errors.head.reason, "Non empty value required")
        assertEquals(errors.head.field, "noneEmptyString")
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

  test("minimalCountSymbols should fail if string does not contains require number of symbols") {
    assert(Validators.minimalCountSymbols(1).validate("minimalCountSymbols", "password").isInvalid)
    assert(Validators.minimalCountSymbols(1).validate("minimalCountSymbols", "123Pasword.#").isValid)

    Validators.minimalCountSymbols(1).validate("minimalCountSymbols", "test") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "min_count_symbols")
        assertEquals(
          errors.head.reason,
          "Count of symbols must be greater or equal 1. Got 0"
        )
        assertEquals(errors.head.field, "minimalCountSymbols")
    }
  }

  test("minimalCountDigits should fail if string does not contains require number of digits") {
    assert(Validators.minimalCountDigits(1).validate("minimalCountDigits", "password").isInvalid)
    assert(Validators.minimalCountDigits(1).validate("minimalCountDigits", "123Pasword.#").isValid)

    Validators.minimalCountDigits(1).validate("minimalCountDigits", "test") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "min_count_digits")
        assertEquals(
          errors.head.reason,
          "Count of digits must be greater or equal 1. Got 0"
        )
        assertEquals(errors.head.field, "minimalCountDigits")
    }
  }

  test("minimalCountLowerCases should fail if string does not contains require number of lower case character") {
    assert(Validators.minimalCountLowerCases(1).validate("minimalCountLowerCases", "PASSWORD").isInvalid)
    assert(Validators.minimalCountLowerCases(1).validate("minimalCountLowerCases", "123Password.#").isValid)

    Validators.minimalCountLowerCases(1).validate("minimalCountLowerCases", "TEST") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "min_count_lower_case")
        assertEquals(
          errors.head.reason,
          "Count of lower cases must be greater or equal 1. Got 0"
        )
        assertEquals(errors.head.field, "minimalCountLowerCases")
    }
  }

  test("minimalCountUpperCases should fail if string does not contains require number of upper case characters") {
    assert(Validators.minimalCountUpperCases(1).validate("minimalCountUpperCases", "password").isInvalid)
    assert(Validators.minimalCountUpperCases(1).validate("minimalCountUpperCases", "123Password.#").isValid)

    Validators.minimalCountUpperCases(1).validate("minimalCountUpperCases", "test") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "min_count_upper_case")
        assertEquals(
          errors.head.reason,
          "Count of upper cases must be greater or equal 1. Got 0"
        )
        assertEquals(errors.head.field, "minimalCountUpperCases")
    }
  }

  test("password should fail if string does is shorter then 8 characters and does not contains 1 symbols, 1 digit, 1 lower and 1 upper case") {
    assert(new Validation("password", "password").is(Validators.password()).validate.isInvalid)
    assert(new Validation("password", "123Pasword.#").is(Validators.password()).validate.isValid)
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
