package pl.muninn.simple.validation

import cats.data.Validated

import pl.muninn.simple.validation.test.OptionalTestClass

class CustomSuite extends munit.FunSuite {
  trait Suite {
    val optionalTestClassSchema: ValidationSchema[OptionalTestClass] = createSchema { context =>
      context.field(_.stringValue).notEmpty +
        context.field(_.intValue).notEmpty +
        context.custom { request =>
          if (request.intValue.contains(10)) {
            context.field(_.stringValue).equalValue(Some("test"))
          } else if (request.intValue.contains(20)) {
            context
              .field(_.stringValue)
              .custom(code = "custom", reason = "Custom validation failed")(_.contains("some other value"))
          } else {
            context.noneValidator
          }
        }
    }

  }

  test("validator should allow to create validation dynamically in easy way") {
    new Suite {
      assert(optionalTestClassSchema.validate(new OptionalTestClass(Some("test"), Some(10))).isValid)
      assert(optionalTestClassSchema.validate(new OptionalTestClass(Some("no test"), Some(10))).isInvalid)
      assert(optionalTestClassSchema.validate(new OptionalTestClass(Some("some other value"), Some(20))).isValid)
      assert(optionalTestClassSchema.validate(new OptionalTestClass(Some("not other value"), Some(20))).isInvalid)
      assert(optionalTestClassSchema.validate(new OptionalTestClass(Some("value"), Some(11))).isValid)
    }
  }

  test("custom filed validator should return custom error") {
    new Suite {
      val result = optionalTestClassSchema.validate(new OptionalTestClass(Some("not other value"), Some(20)))
      assert(result.isInvalid)
      result match {
        case Validated.Valid(_) => fail("Results should be invalid")
        case Validated.Invalid(e) =>
          assertEquals(e.length, 1L)
          assertEquals(e.map(_.field).iterator.toList, List("stringValue"))
          assertEquals(e.map(_.code).iterator.toList, List("custom"))
          assertEquals(e.map(_.reason).iterator.toList, List("Custom validation failed"))
      }
    }
  }
}
