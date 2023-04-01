package pl.muninn.simple.validation.validator.typed

import cats.data.Validated

class CollectionValidatorsSpec extends munit.FunSuite {

  test("all should run validator on all values") {
    assert(CollectionValidators.all(StringValidators.notEmptyString).validate("all", List("test", "")).isInvalid)
    assert(CollectionValidators.all(StringValidators.notEmptyString).validate("all", List("test", "test2")).isValid)

    CollectionValidators.all(StringValidators.notEmptyString).validate("all", List("test", "", "test2", "", "test3", "")) match {
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

  test("notEmptyCollection should fail if collection is empty") {
    assert(CollectionValidators.notEmptyCollection.validate("notEmptyCollection", List.empty).isInvalid)
    assert(CollectionValidators.notEmptyCollection.validate("notEmptyCollection", Seq.empty).isInvalid)
    assert(CollectionValidators.notEmptyCollection.validate("notEmptyCollection", List("")).isValid)

    CollectionValidators.notEmptyCollection.validate("notEmptyCollection", List.empty) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_field")
        assertEquals(errors.head.reason, "Non empty value required")
        assertEquals(errors.head.field, "notEmptyCollection")
    }
  }

  test("emptyCollection should fail if collection is not empty") {
    assert(CollectionValidators.emptyCollection.validate("emptyCollection", List.empty).isValid)
    assert(CollectionValidators.emptyCollection.validate("emptyCollection", Seq.empty).isValid)
    assert(CollectionValidators.emptyCollection.validate("emptyCollection", List("")).isInvalid)

    CollectionValidators.emptyCollection.validate("emptyCollection", List("")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_expected")
        assertEquals(errors.head.reason, "Value must be empty")
        assertEquals(errors.head.field, "emptyCollection")
    }
  }

  test("minimalLengthCollection should fail when collection length is below minimal value") {
    assert(CollectionValidators.minimalLengthCollection(2).validate("minimalLengthCollection", List("test", "test2", "test3")).isValid)
    assert(CollectionValidators.minimalLengthCollection(2).validate("minimalLengthCollection", Seq.empty).isInvalid)
    assert(CollectionValidators.minimalLengthCollection(2).validate("minimalLengthCollection", List("test", "test")).isValid)

    CollectionValidators.minimalLengthCollection(2).validate("minimalLengthCollection", List.empty) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "minimal_length")
        assertEquals(errors.head.reason, "Length must be greater or equal 2. Got 0")
        assertEquals(errors.head.field, "minimalLengthCollection")
    }
  }

  test("maximalLengthCollection should fail when collection length is below minimal value") {
    assert(CollectionValidators.maximalLengthCollection(1).validate("maximalLengthCollection", List("test")).isValid)
    assert(CollectionValidators.maximalLengthCollection(1).validate("maximalLengthCollection", Seq.empty).isValid)
    assert(CollectionValidators.maximalLengthCollection(1).validate("maximalLengthCollection", List("test", "test")).isInvalid)

    CollectionValidators.maximalLengthCollection(1).validate("maximalLengthCollection", List("test", "test2")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "maximal_length")
        assertEquals(errors.head.reason, "Length must be lower or equal 1. Got 2")
        assertEquals(errors.head.field, "maximalLengthCollection")
    }
  }

  test("exactLengthCollection should fail if collection length is not as expected") {
    assert(CollectionValidators.exactLengthCollection(1).validate("exactLengthCollection", List("test")).isValid)
    assert(CollectionValidators.exactLengthCollection(1).validate("exactLengthCollection", Seq.empty).isInvalid)
    assert(CollectionValidators.exactLengthCollection(1).validate("exactLengthCollection", List("")).isValid)

    CollectionValidators.exactLengthCollection(1).validate("exactLengthCollection", List.empty) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "expected_length")
        assertEquals(errors.head.reason, "Length must be equal 1. Got 0")
        assertEquals(errors.head.field, "exactLengthCollection")
    }
  }
}
