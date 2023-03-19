package pl.muninn.simple.validation.validators

import cats.data.Validated

class MapValidatorsSpec extends munit.FunSuite {

  test("containsKey should fail when map is not containing defined key") {
    assert(MapValidators.containsKey("test").validate("containsKey", Map.empty).isInvalid)
    assert(MapValidators.containsKey("test").validate("containsKey", Map("other" -> "key")).isInvalid)
    assert(MapValidators.containsKey("test").validate("containsKey", Map("test" -> "key")).isValid)

    MapValidators.containsKey("test").validate("containsKey", Map("other" -> "key")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "key_missing")
        assertEquals(errors.head.reason, "Key test is missing")
        assertEquals(errors.head.field, "containsKey")
    }
  }

  test("containsKeys should fail when map is not containing defined key") {
    assert(MapValidators.containsKeys(List("test")).validate("containsKeys", Map.empty).isInvalid)
    assert(MapValidators.containsKeys(List("test")).validate("containsKeys", Map("other" -> "key")).isInvalid)
    assert(MapValidators.containsKeys(List("test")).validate("containsKeys", Map("test" -> "key")).isValid)

    MapValidators.containsKeys(List("test", "test2")).validate("containsKeys", Map("other" -> "key")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "keys_missing")
        assertEquals(errors.head.reason, "Keys test, test2 are missing")
        assertEquals(errors.head.field, "containsKeys")
    }
  }

  test("containsKeys should fail different when only 1 key is missing") {
    MapValidators.containsKeys(List("test", "test2")).validate("containsKeys", Map("test" -> "key")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "key_missing")
        assertEquals(errors.head.reason, "Key test2 is missing")
        assertEquals(errors.head.field, "containsKeys")
    }
  }

  test("notEmpty should fail if collection is empty") {
    assert(MapValidators.notEmpty.validate("notEmptyMap", Map.empty).isInvalid)
    assert(MapValidators.notEmpty.validate("notEmptyMap", Map("test" -> "test")).isValid)

    MapValidators.notEmpty.validate("notEmptyMap", Map.empty) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_field")
        assertEquals(errors.head.reason, "Non empty value required")
        assertEquals(errors.head.field, "notEmptyMap")
    }
  }

  test("empty should fail if collection is not empty") {
    assert(MapValidators.empty.validate("emptyMap", Map.empty).isValid)
    assert(MapValidators.empty.validate("emptyMap", Map("test" -> "test")).isInvalid)

    MapValidators.empty.validate("emptyMap", Map("test" -> "test")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_expected")
        assertEquals(errors.head.reason, "Value must be empty")
        assertEquals(errors.head.field, "emptyMap")
    }
  }

  test("minimalLength should fail when collection length is below minimal value") {
    assert(MapValidators.minimalLength(1).validate("minimalLengthMap", Map("test" -> "test", "test2" -> "test")).isValid)
    assert(MapValidators.minimalLength(1).validate("minimalLengthMap", Map.empty).isInvalid)
    assert(MapValidators.minimalLength(1).validate("minimalLengthMap", Map("test" -> "test")).isValid)

    MapValidators.minimalLength(2).validate("minimalLengthMap", Map.empty) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "minimal_length")
        assertEquals(errors.head.reason, "Length must be greater or equal 2. Got 0")
        assertEquals(errors.head.field, "minimalLengthMap")
    }
  }

  test("maximalLength should fail when collection length is below minimal value") {
    assert(MapValidators.maximalLength(1).validate("maximalLengthMap", Map("test" -> "test")).isValid)
    assert(MapValidators.maximalLength(1).validate("maximalLengthMap", Map.empty).isValid)
    assert(MapValidators.maximalLength(1).validate("maximalLengthMap", Map("test" -> "test", "test2" -> "test")).isInvalid)

    MapValidators.maximalLength(1).validate("maximalLengthMap", Map("test" -> "test", "test2" -> "test")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "maximal_length")
        assertEquals(errors.head.reason, "Length must be lower or equal 1. Got 2")
        assertEquals(errors.head.field, "maximalLengthMap")
    }
  }

  test("exactLengthMap should fail if collection length is not as expected") {
    assert(MapValidators.exactLength(1).validate("exactLengthMap", Map("test" -> "test")).isValid)
    assert(MapValidators.exactLength(1).validate("exactLengthMap", Map.empty).isInvalid)

    MapValidators.exactLength(1).validate("exactLengthMap", Map.empty) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "expected_length")
        assertEquals(errors.head.reason, "Length must be equal 1. Got 0")
        assertEquals(errors.head.field, "exactLengthMap")
    }
  }
}
