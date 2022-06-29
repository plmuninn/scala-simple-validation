package pl.muninn.simple.validation

import all._

case class TestClass(value: String, values: List[String])

class ImplicitSuite extends munit.FunSuite {

  test("allow implicit composition") {
    val schema =
      ValidationSchemaContext.createSchema[TestClass] { context =>
        context.field("value")(_.value).isNonEmptyString +
          (context.field("values")(_.values).nonEmpty and all(nonEmptyString))
      }

    assert(schema.validate(new TestClass("test", List.empty)).isInvalid)
    assert(schema.validate(new TestClass("test", List(""))).isInvalid)
    assert(schema.validate(new TestClass("test", List("test"))).isValid)
  }
}
