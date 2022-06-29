package pl.muninn.simple.validation.validators

import cats.data.Validated

import pl.muninn.simple.validation.Validators

class CollectionValidatorsSpec extends munit.FunSuite {

  test("all should run validator on all values") {
    assert(Validators.all(Validators.nonEmptyString).validate("all", List("test", "")).isInvalid)
    assert(Validators.all(Validators.nonEmptyString).validate("all", List("test", "test2")).isValid)

    Validators.all(Validators.nonEmptyString).validate("all", List("test", "", "test2", "", "test3", "")) match {
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
    assert(Validators.noneEmptyCollection.validate("nonEmptyCollection", List.empty).isInvalid)
    assert(Validators.noneEmptyCollection.validate("nonEmptyCollection", Seq.empty).isInvalid)
    assert(Validators.noneEmptyCollection.validate("nonEmptyCollection", List("")).isValid)

    Validators.noneEmptyCollection.validate("nonEmptyCollection", List.empty) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_field")
        assertEquals(errors.head.reason, "Non empty value required")
        assertEquals(errors.head.field, "nonEmptyCollection")
    }
  }

  test("emptyCollection should fail if collection is not empty") {
    assert(Validators.emptyCollection.validate("emptyCollection", List.empty).isValid)
    assert(Validators.emptyCollection.validate("emptyCollection", Seq.empty).isValid)
    assert(Validators.emptyCollection.validate("emptyCollection", List("")).isInvalid)

    Validators.emptyCollection.validate("emptyCollection", List("")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "empty_expected")
        assertEquals(errors.head.reason, "Value must be empty")
        assertEquals(errors.head.field, "emptyCollection")
    }
  }

  test("collectionMinimalLength should fail when collection length is below minimal value") {
    assert(Validators.collectionMinimalLength(2).validate("collectionMinimalLength", List("test", "test2", "test3")).isValid)
    assert(Validators.collectionMinimalLength(2).validate("collectionMinimalLength", Seq.empty).isInvalid)
    assert(Validators.collectionMinimalLength(2).validate("collectionMinimalLength", List("test", "test")).isValid)

    Validators.collectionMinimalLength(2).validate("collectionMinimalLength", List.empty) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "minimal_length")
        assertEquals(errors.head.reason, "Length must be greater or equal 2. Got 0")
        assertEquals(errors.head.field, "collectionMinimalLength")
    }
  }

  test("collectionMaximalLength should fail when collection length is below minimal value") {
    assert(Validators.collectionMaximalLength(1).validate("collectionMaximalLength", List("test")).isValid)
    assert(Validators.collectionMaximalLength(1).validate("collectionMaximalLength", Seq.empty).isValid)
    assert(Validators.collectionMaximalLength(1).validate("collectionMaximalLength", List("test", "test")).isInvalid)

    Validators.collectionMaximalLength(1).validate("collectionMaximalLength", List("test", "test2")) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "maximal_length")
        assertEquals(errors.head.reason, "Length must be lower or equal 1. Got 2")
        assertEquals(errors.head.field, "collectionMaximalLength")
    }
  }

  test("collectionLength should fail if collection length is not as expected") {
    assert(Validators.collectionLength(1).validate("collectionLength", List("test")).isValid)
    assert(Validators.collectionLength(1).validate("collectionLength", Seq.empty).isInvalid)
    assert(Validators.collectionLength(1).validate("collectionLength", List("")).isValid)

    Validators.collectionLength(1).validate("collectionLength", List.empty) match {
      case Validated.Valid(_) => fail("Result should be invalid")
      case Validated.Invalid(errors) =>
        assertEquals(errors.length, 1L)
        assertEquals(errors.head.code, "expected_length")
        assertEquals(errors.head.reason, "Length must be equal 1. Got 0")
        assertEquals(errors.head.field, "collectionLength")
    }
  }
}
