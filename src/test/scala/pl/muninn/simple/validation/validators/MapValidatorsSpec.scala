package pl.muninn.simple.validation.validators

import cats.data.Validated

import pl.muninn.simple.validation.Validators

class MapValidatorsSpec extends munit.FunSuite {

  test("containsKey should fail when map is not containing defined key") {
    assert(Validators.containsKey("test").validate("containsKey", Map.empty).isInvalid)
    assert(Validators.containsKey("test").validate("containsKey", Map("other" -> "key")).isInvalid)
    assert(Validators.containsKey("test").validate("containsKey", Map("test" -> "key")).isValid)

    Validators.containsKey("test").validate("containsKey", Map("other" -> "key")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "key_missing")
        assertEquals(errors.head.reason, "Key test is missing")
        assertEquals(errors.head.field, "containsKey")
    }
  }

  test("containsKeys should fail when map is not containing defined key") {
    assert(Validators.containsKeys(List("test")).validate("containsKeys", Map.empty).isInvalid)
    assert(Validators.containsKeys(List("test")).validate("containsKeys", Map("other" -> "key")).isInvalid)
    assert(Validators.containsKeys(List("test")).validate("containsKeys", Map("test" -> "key")).isValid)

    Validators.containsKeys(List("test", "test2")).validate("containsKeys", Map("other" -> "key")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "keys_missing")
        assertEquals(errors.head.reason, "Keys test, test2 are missing")
        assertEquals(errors.head.field, "containsKeys")
    }
  }

  test("containsKeys should fail different when only 1 key is missing") {
    Validators.containsKeys(List("test", "test2")).validate("containsKeys", Map("test" -> "key")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "key_missing")
        assertEquals(errors.head.reason, "Key test2 is missing")
        assertEquals(errors.head.field, "containsKeys")
    }
  }
}
