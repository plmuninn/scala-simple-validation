package pl.muninn.simple.validation

import cats.data.Validated

import pl.muninn.simple.validation.all._
import pl.muninn.simple.validation.test.ValueType
import pl.muninn.simple.validation.validators.StringValidators

class CustomValidatorsSpec extends munit.FunSuite {

  case class TestWithSchema(valueType: ValueType, otherValue: String)

  trait Suit {
    val notEmptyValueValidatorValidator: ValueValidator[ValueType] = StringValidators.notEmptyString.contramap(_.value)

    val valueTypeSchema = createSchema[ValueType] { context =>
      context.field(_.value).notEmpty
    }

    val classSchema = createSchema[TestWithSchema] { context =>
      context.field(_.otherValue).notEmpty +
        context.field(_.valueType).is(valueTypeSchema)
    }
  }

  test("contramap should properly change validator") {
    new Suit {
      notEmptyValueValidatorValidator.validate("test", ValueType("")) match {
        case Validated.Valid(_) => fail("Result should be invalid")
        case Validated.Invalid(errors) =>
          assertEquals(errors.length, 1L)
          assertEquals(errors.head.code, "empty_field")
          assertEquals(errors.head.reason, "Non empty value required")
          assertEquals(errors.head.field, "test")
      }
    }
  }

  test("you should be able to use schema as validator") {
    new Suit {
      classSchema.validate(TestWithSchema(ValueType(""), "")) match {
        case Validated.Valid(_) => fail("Result should be invalid")
        case Validated.Invalid(errors) =>
          assertEquals(errors.length, 2L)
          for (error <- errors) yield {
            assertEquals(error.code, "empty_field")
            assertEquals(error.reason, "Non empty value required")
          }

          assertEquals(errors.head.field, "otherValue")
          assertEquals(errors.last.field, "valueType.value")
      }
    }
  }

}
