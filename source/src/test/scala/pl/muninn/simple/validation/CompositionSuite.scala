package pl.muninn.simple.validation

import cats.data.{Validated, ValidatedNec}

import pl.muninn.simple.validation.model.InvalidField
import pl.muninn.simple.validation.test.{OptionalTestClass, PairTestClass, TypeTestClass}

class CompositionSuite extends munit.FunSuite {

  trait Suit {
    val typeTestClassSchema: ValidationSchema[TypeTestClass] = createSchema { context =>
      context.field("stringValue")(_.stringValue).is(notEmptyString) +
        context.field("intValue")(_.intValue).is(min(10)) +
        context.field("listValue")(_.listValue).is(all(notEmptyString) and notEmptyCollection) +
        context.field("mapValue")(_.mapValue).is(containsKey("test"))
    }

    val pairTestClassSchema: ValidationSchema[PairTestClass] = createSchema { context =>
      context.pair("value1")(_.value1)("value2")(_.value2).is(fieldsEqual)
    }

    val optionalTestClassSchema: ValidationSchema[OptionalTestClass] = createSchema { context =>
      context.field("stringValue")(_.stringValue).is(ifDefined(notEmptyString) and defined) +
        context.field("intValue")(_.intValue).is(ifDefined(min(10)))
    }
  }

  test("classes should return is valid if all of the rules are met") {
    new Suit {
      assert(typeTestClassSchema.validate(TypeTestClass("test", 11, List("test"), Map("test" -> "test"))).isValid)
      assert(pairTestClassSchema.validate(PairTestClass("test", "test")).isValid)
      assert(optionalTestClassSchema.validate(OptionalTestClass(Some("test"), Some(11))).isValid)
    }
  }

  test("classes should fail validation in at least one of rules is not met") {
    new Suit {
      assert(typeTestClassSchema.validate(TypeTestClass("test", 11, List.empty, Map("test" -> "test"))).isInvalid)
      assert(pairTestClassSchema.validate(PairTestClass("test", "test2")).isInvalid)
      assert(optionalTestClassSchema.validate(OptionalTestClass(None, None)).isInvalid)
    }
  }

  test("classes return list of errors") {
    new Suit {
      val result: ValidatedNec[InvalidField, Unit] = typeTestClassSchema.validate(TypeTestClass("", 5, List.empty, Map("test2" -> "test")))
      assert(result.isInvalid)
      result match {
        case Validated.Valid(_) => fail("Results should be invalid")
        case Validated.Invalid(e) =>
          assertEquals(e.length, 4L)
          assertEquals(e.map(_.field).iterator.toList, List("stringValue", "intValue", "listValue", "mapValue"))
          assertEquals(e.map(_.code).iterator.toList, List("empty_field", "minimal_value", "empty_field", "key_missing"))
      }
    }
  }

  test("classes return list of fields in array") {
    new Suit {
      val result: ValidatedNec[InvalidField, Unit] =
        typeTestClassSchema.validate(TypeTestClass("test", 11, List("", "test", "", "test2"), Map("test" -> "test")))
      assert(result.isInvalid)
      result match {
        case Validated.Valid(_) => fail("Results should be invalid")
        case Validated.Invalid(e) =>
          assertEquals(e.length, 2L)
          assertEquals(e.map(_.field).iterator.toList, List("listValue.0", "listValue.2"))
      }
    }
  }
}
