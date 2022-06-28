package pl.muninn.simple.validation

import cats.data.Validated

class CommonValidatorsSpec extends munit.FunSuite {

  test("nonEmptyString should fail if string is empty and pass if isn't") {
    assert(CommonValidators.nonEmptyString.validate("nonEmptyStringTest", "").isInvalid)
    assert(CommonValidators.nonEmptyString.validate("nonEmptyStringTest", "t").isValid)

    CommonValidators.nonEmptyString.validate("nonEmptyStringTest", "") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_field")
        assertEquals(errors.head.reason, "Non empty value required")
        assertEquals(errors.head.field, "nonEmptyStringTest")
    }
  }

  test("emailString should fail if string is not an email and pass if is") {
    assert(CommonValidators.emailString.validate("emailTest", "some string").isInvalid)
    assert(CommonValidators.emailString.validate("emailTest", "test@example.com").isValid)

    CommonValidators.emailString.validate("emailTest", "") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "email_field")
        assertEquals(errors.head.reason, "Value must be a valid email")
        assertEquals(errors.head.field, "emailTest")
    }
  }

  test("complexPasswordRegex should fail if string is not an 8 symbols long with big and small letters, number and symbols and pass if is") {
    assert(CommonValidators.complexPassword.validate("passwordTest", "password").isInvalid)
    assert(CommonValidators.complexPassword.validate("passwordTest", "123Pasword.#").isValid)

    CommonValidators.complexPassword.validate("passwordTest", "test") match {
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

  test("fieldsEqual should fail if fields are not equal") {
    assert(CommonValidators.fieldsEqual.validate("fieldsEqual", ("password", "pass")).isInvalid)
    assert(CommonValidators.fieldsEqual.validate("fieldsEqual", ("password", "password")).isValid)

    CommonValidators.fieldsEqual.validate("fieldsEqual", ("password", "")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "fields_not_equal")
        assertEquals(errors.head.reason, "Values fieldsEqual are not equal")
        assertEquals(errors.head.field, "fieldsEqual")
    }
  }

  test("forOpt should test only value when is defined, otherwise it should pass it as valid") {
    assert(CommonValidators.forOpt(CommonValidators.nonEmptyString).validate("forOpt", Some("")).isInvalid)
    assert(CommonValidators.forOpt(CommonValidators.nonEmptyString).validate("forOpt", Some("some")).isValid)
    assert(CommonValidators.forOpt(CommonValidators.nonEmptyString).validate("forOpt", None).isValid)
  }

  test("isDefined should fail if is not defined optional value") {
    assert(CommonValidators.isDefined.validate("isDefined", None).isInvalid)
    assert(CommonValidators.isDefined.validate("isDefined", Some("")).isValid)

    CommonValidators.isDefined.validate("isDefined", None) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_field")
        assertEquals(errors.head.reason, "Non empty value required")
        assertEquals(errors.head.field, "isDefined")
    }
  }

  test("notDefined should fail if value is defined for optional value") {
    assert(CommonValidators.notDefined.validate("notDefined", Some("")).isInvalid)
    assert(CommonValidators.notDefined.validate("notDefined", None).isValid)

    CommonValidators.notDefined.validate("notDefined", Some("")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_expected")
        assertEquals(errors.head.reason, "Value must be empty")
        assertEquals(errors.head.field, "notDefined")
    }
  }

  test("all should run validator on all values") {
    assert(CommonValidators.all(CommonValidators.nonEmptyString).validate("all", List("test", "")).isInvalid)
    assert(CommonValidators.all(CommonValidators.nonEmptyString).validate("all", List("test", "test2")).isValid)

    CommonValidators.all(CommonValidators.nonEmptyString).validate("all", List("test", "", "test2", "", "test3", "")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 3L)
        for (err <- errors) yield {
          assertEquals(err.code, "empty_field")
          assertEquals(err.reason, "Non empty value required")
        }
        assertEquals(errors.map(_.field).toNonEmptyList.toList, List("all.1", "all.3", "all.5"))
    }
  }

  test("nonEmptyCollection should fail if collection is empty") {
    assert(CommonValidators.noneEmptyCollection.validate("nonEmptyCollection", List.empty).isInvalid)
    assert(CommonValidators.noneEmptyCollection.validate("nonEmptyCollection", Seq.empty).isInvalid)
    assert(CommonValidators.noneEmptyCollection.validate("nonEmptyCollection", List("")).isValid)

    CommonValidators.noneEmptyCollection.validate("nonEmptyCollection", List.empty) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_field")
        assertEquals(errors.head.reason, "Non empty value required")
        assertEquals(errors.head.field, "nonEmptyCollection")
    }
  }

  test("emptyCollection should fail if collection is not empty") {
    assert(CommonValidators.emptyCollection.validate("emptyCollection", List.empty).isValid)
    assert(CommonValidators.emptyCollection.validate("emptyCollection", Seq.empty).isValid)
    assert(CommonValidators.emptyCollection.validate("emptyCollection", List("")).isInvalid)

    CommonValidators.emptyCollection.validate("emptyCollection", List("")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_expected")
        assertEquals(errors.head.reason, "Value must be empty")
        assertEquals(errors.head.field, "emptyCollection")
    }
  }

  test("minimalNumberValue should fail when value is below minimal value") {
    assert(CommonValidators.minimalNumberValue(10).validate("minimalNumberValue", 10).isValid)
    assert(CommonValidators.minimalNumberValue(10).validate("minimalNumberValue", 11).isValid)
    assert(CommonValidators.minimalNumberValue(10).validate("minimalNumberValue", 9).isInvalid)

    CommonValidators.minimalNumberValue(10).validate("minimalNumberValue", 9) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "minimal_value")
        assertEquals(errors.head.reason, "Value must be greater or equal 10. Got 9")
        assertEquals(errors.head.field, "minimalNumberValue")
    }
  }

  test("maximalNumberValue should fail when value is above maximum value") {
    assert(CommonValidators.maximalNumberValue(10).validate("maximalNumberValue", 10).isValid)
    assert(CommonValidators.maximalNumberValue(10).validate("maximalNumberValue", 11).isInvalid)
    assert(CommonValidators.maximalNumberValue(10).validate("maximalNumberValue", 9).isValid)

    CommonValidators.maximalNumberValue(10).validate("maximalNumberValue", 11) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "maximal_value")
        assertEquals(errors.head.reason, "Value must be lower or equal 10. Got 11")
        assertEquals(errors.head.field, "maximalNumberValue")
    }
  }
}
