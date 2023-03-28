package pl.muninn.simple.validation.validator.typed

import cats.data.Validated

import pl.muninn.simple.validation.model.Field

class StringValidatorsSpec extends munit.FunSuite {

  test("emptyString should fail if string is empty and pass if isn't") {
    assert(StringValidators.emptyString.validate("emptyString", "t").isInvalid)
    assert(StringValidators.emptyString.validate("emptyString", "").isValid)

    StringValidators.emptyString.validate("emptyString", "t") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_expected")
        assertEquals(errors.head.reason, "Value must be empty")
        assertEquals(errors.head.field, "emptyString")
    }
  }

  test("noneEmptyString should fail if string is empty and pass if isn't") {
    assert(StringValidators.notEmptyString.validate("notEmptyString", "").isInvalid)
    assert(StringValidators.notEmptyString.validate("notEmptyString", "t").isValid)

    StringValidators.notEmptyString.validate("notEmptyString", "") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_field")
        assertEquals(errors.head.reason, "Non empty value required")
        assertEquals(errors.head.field, "notEmptyString")
    }
  }

  test("email should fail if string is not an email and pass if is") {
    assert(StringValidators.email.validate("email", "some string").isInvalid)
    assert(StringValidators.email.validate("email", "test@example.com").isValid)

    StringValidators.email.validate("email", "") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "email_field")
        assertEquals(errors.head.reason, "Value must be a valid email")
        assertEquals(errors.head.field, "email")
    }
  }

  test("minimalCountSymbols should fail if string does not contains require number of symbols") {
    assert(StringValidators.minimalCountSymbols(1).validate("minimalCountSymbols", "password").isInvalid)
    assert(StringValidators.minimalCountSymbols(1).validate("minimalCountSymbols", "123Pasword.#").isValid)

    StringValidators.minimalCountSymbols(1).validate("minimalCountSymbols", "test") match {
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
    assert(StringValidators.minimalCountDigits(1).validate("minimalCountDigits", "password").isInvalid)
    assert(StringValidators.minimalCountDigits(1).validate("minimalCountDigits", "123Pasword.#").isValid)

    StringValidators.minimalCountDigits(1).validate("minimalCountDigits", "test") match {
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
    assert(StringValidators.minimalCountLowerCases(1).validate("minimalCountLowerCases", "PASSWORD").isInvalid)
    assert(StringValidators.minimalCountLowerCases(1).validate("minimalCountLowerCases", "123Password.#").isValid)

    StringValidators.minimalCountLowerCases(1).validate("minimalCountLowerCases", "TEST") match {
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
    assert(StringValidators.minimalCountUpperCases(1).validate("minimalCountUpperCases", "password").isInvalid)
    assert(StringValidators.minimalCountUpperCases(1).validate("minimalCountUpperCases", "123Password.#").isValid)

    StringValidators.minimalCountUpperCases(1).validate("minimalCountUpperCases", "test") match {
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
    assert(new Field("password", "password").is(StringValidators.password()).validate.isInvalid)
    assert(new Field("password", "123Pasword.#").is(StringValidators.password()).validate.isValid)
  }

  test("minimalLengthString should fail string is shorter then minimal length") {
    assert(StringValidators.minimalLengthString(2).validate("minimalLengthString", "t").isInvalid)
    assert(StringValidators.minimalLengthString(2).validate("minimalLengthString", "test").isValid)
    assert(StringValidators.minimalLengthString(2).validate("minimalLengthString", "te").isValid)

    StringValidators.minimalLengthString(2).validate("minimalLengthString", "1") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "minimal_length")
        assertEquals(errors.head.reason, "Length must be greater or equal 2. Got 1")
        assertEquals(errors.head.field, "minimalLengthString")
    }
  }

  test("maximalLength should fail string is longer then maximal length") {
    assert(StringValidators.maximalLengthString(3).validate("maximalLengthString", "test").isInvalid)
    assert(StringValidators.maximalLengthString(3).validate("maximalLengthString", "tes").isValid)
    assert(StringValidators.maximalLengthString(3).validate("maximalLengthString", "").isValid)

    StringValidators.maximalLengthString(3).validate("maximalLengthString", "test") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "maximal_length")
        assertEquals(errors.head.reason, "Length must be lower or equal 3. Got 4")
        assertEquals(errors.head.field, "maximalLengthString")
    }
  }

  test("exactLength should fail string is has different length then expected") {
    assert(StringValidators.exactLengthString(4).validate("exactLengthString", "tes").isInvalid)
    assert(StringValidators.exactLengthString(4).validate("exactLengthString", "test").isValid)
    assert(StringValidators.exactLengthString(4).validate("exactLengthString", "").isInvalid)

    StringValidators.exactLengthString(4).validate("exactLengthString", "tes") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "expected_length")
        assertEquals(errors.head.reason, "Length must be equal 4. Got 3")
        assertEquals(errors.head.field, "exactLengthString")
    }
  }

  test("contains should fail string is does not contain expected value") {
    assert(StringValidators.contains("not").validate("containsString", "test").isInvalid)
    assert(StringValidators.contains("te").validate("containsString", "test").isValid)

    StringValidators.contains("not").validate("containsString", "test") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "value_contains")
        assertEquals(errors.head.reason, "Value should contain not")
        assertEquals(errors.head.field, "containsString")
    }
  }

  test("containsAtLeastOne should fail string does not contain at least one of values") {
    assert(StringValidators.containsAtLeastOne(List("not", "yes")).validate("containsOneOfString", "test").isInvalid)
    assert(StringValidators.containsAtLeastOne(List("not", "te")).validate("containsOneOfString", "test").isValid)

    StringValidators.containsAtLeastOne(List("not", "yes")).validate("containsOneOfString", "test") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "one_of_values_contains")
        assertEquals(errors.head.reason, "One of values not, yes should be found")
        assertEquals(errors.head.field, "containsOneOfString")
    }
  }

  test("equalAtLeastOne should fail string is not equal at least one of values") {
    assert(StringValidators.equalAtLeastOne(List("not", "yes")).validate("equalAtLeastOneString", "test").isInvalid)
    assert(StringValidators.equalAtLeastOne(List("not", "test")).validate("equalAtLeastOneString", "test").isValid)

    StringValidators.equalAtLeastOne(List("not", "yes")).validate("equalAtLeastOneString", "test") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "one_of_values_missing")
        assertEquals(errors.head.reason, "One of values not, yes is missing")
        assertEquals(errors.head.field, "equalAtLeastOneString")
    }
  }
}
