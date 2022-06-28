package pl.muninn.simple.validation

import cats.data.Validated

class CommonValidatorsSpec extends munit.FunSuite {

  test("equalValue should fail if values are not equal") {
    assert(CommonValidators.equalValue("test").validate("equalValue", "test").isValid)
    assert(CommonValidators.equalValue("test").validate("equalValue", "").isInvalid)
    assert(CommonValidators.equalValue(1).validate("equalValue", 1).isValid)
    assert(CommonValidators.equalValue(1).validate("equalValue", 2).isInvalid)

    CommonValidators.equalValue("test").validate("equalValue", "t") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "equal_field")
        assertEquals(errors.head.reason, "Value not equal test. Got t")
        assertEquals(errors.head.field, "equalValue")
    }
  }

  test("customValid should fail if testing function returns false") {
    assert(CommonValidators.customValid[String]("testCode", _ => "Is not empty")(_.isEmpty).validate("customValid", "test").isInvalid)
    assert(CommonValidators.customValid[String]("testCode", _ => "Is not empty")(_.isEmpty).validate("customValid", "").isValid)

    CommonValidators.customValid[String]("testCode", _ => "Is not empty")(_.isEmpty).validate("customValid", "test") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "testCode")
        assertEquals(errors.head.reason, "Is not empty")
        assertEquals(errors.head.field, "customValid")
    }
  }

  test("emptyString should fail if string is empty and pass if isn't") {
    assert(CommonValidators.emptyString.validate("nonEmptyStringTest", "t").isInvalid)
    assert(CommonValidators.emptyString.validate("nonEmptyStringTest", "").isValid)

    CommonValidators.emptyString.validate("emptyString", "t") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_expected")
        assertEquals(errors.head.reason, "Value must be empty")
        assertEquals(errors.head.field, "emptyString")
    }
  }

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

  test("stringMinimalLength should fail string is shorter then minimal length") {
    assert(CommonValidators.stringMinimalLength(2).validate("stringMinimalLength", "t").isInvalid)
    assert(CommonValidators.stringMinimalLength(2).validate("stringMinimalLength", "test").isValid)
    assert(CommonValidators.stringMinimalLength(2).validate("stringMinimalLength", "te").isValid)

    CommonValidators.stringMinimalLength(2).validate("stringMinimalLength", "1") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "minimal_length")
        assertEquals(errors.head.reason, "Length must be greater or equal 2. Got 1")
        assertEquals(errors.head.field, "stringMinimalLength")
    }
  }

  test("stringMaximalLength should fail string is longer then maximal length") {
    assert(CommonValidators.stringMaximalLength(3).validate("stringMaximalLength", "test").isInvalid)
    assert(CommonValidators.stringMaximalLength(3).validate("stringMaximalLength", "tes").isValid)
    assert(CommonValidators.stringMaximalLength(3).validate("stringMaximalLength", "").isValid)

    CommonValidators.stringMaximalLength(3).validate("stringMaximalLength", "test") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "maximal_length")
        assertEquals(errors.head.reason, "Length must be lower or equal 3. Got 4")
        assertEquals(errors.head.field, "stringMaximalLength")
    }
  }

  test("stringLength should fail string is has different length then expected") {
    assert(CommonValidators.stringLength(4).validate("stringLength", "tes").isInvalid)
    assert(CommonValidators.stringLength(4).validate("stringLength", "test").isValid)
    assert(CommonValidators.stringLength(4).validate("stringLength", "").isInvalid)

    CommonValidators.stringLength(4).validate("stringLength", "tes") match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "expected_length")
        assertEquals(errors.head.reason, "Length must be equal 4. Got 3")
        assertEquals(errors.head.field, "stringLength")
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

  test("collectionMinimalLength should fail when collection length is below minimal value") {
    assert(CommonValidators.collectionMinimalLength(2).validate("collectionMinimalLength", List("test", "test2", "test3")).isValid)
    assert(CommonValidators.collectionMinimalLength(2).validate("collectionMinimalLength", Seq.empty).isInvalid)
    assert(CommonValidators.collectionMinimalLength(2).validate("collectionMinimalLength", List("test", "test")).isValid)

    CommonValidators.collectionMinimalLength(2).validate("collectionMinimalLength", List.empty) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "minimal_length")
        assertEquals(errors.head.reason, "Length must be greater or equal 2. Got 0")
        assertEquals(errors.head.field, "collectionMinimalLength")
    }
  }

  test("collectionMaximalLength should fail when collection length is below minimal value") {
    assert(CommonValidators.collectionMaximalLength(1).validate("collectionMaximalLength", List("test")).isValid)
    assert(CommonValidators.collectionMaximalLength(1).validate("collectionMaximalLength", Seq.empty).isValid)
    assert(CommonValidators.collectionMaximalLength(1).validate("collectionMaximalLength", List("test", "test")).isInvalid)

    CommonValidators.collectionMaximalLength(1).validate("collectionMaximalLength", List("test", "test2")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "maximal_length")
        assertEquals(errors.head.reason, "Length must be lower or equal 1. Got 2")
        assertEquals(errors.head.field, "collectionMaximalLength")
    }
  }

  test("collectionLength should fail if collection length is not as expected") {
    assert(CommonValidators.collectionLength(1).validate("collectionLength", List("test")).isValid)
    assert(CommonValidators.collectionLength(1).validate("collectionLength", Seq.empty).isInvalid)
    assert(CommonValidators.collectionLength(1).validate("collectionLength", List("")).isValid)

    CommonValidators.collectionLength(1).validate("collectionLength", List.empty) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "expected_length")
        assertEquals(errors.head.reason, "Length must be equal 1. Got 0")
        assertEquals(errors.head.field, "collectionLength")
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

  test("numberEqual should fail when value is not equal expected ") {
    assert(CommonValidators.numberEqual(10).validate("numberEqual", 10).isValid)
    assert(CommonValidators.numberEqual(10).validate("numberEqual", 11).isInvalid)
    assert(CommonValidators.numberEqual(10).validate("numberEqual", 9).isInvalid)

    CommonValidators.numberEqual(10).validate("numberEqual", 11) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "expected_value")
        assertEquals(errors.head.reason, "Value must be equal 10. Got 11")
        assertEquals(errors.head.field, "numberEqual")
    }
  }
}
