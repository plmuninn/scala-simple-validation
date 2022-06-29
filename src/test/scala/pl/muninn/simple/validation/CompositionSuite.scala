package pl.muninn.simple.validation

import all._

import pl.muninn.simple.validation.test.{OptionalTestClass, PairTestClass, TypeTestClass}

class CompositionSuite extends munit.FunSuite {
  trait Suit {
    val typeTestClassSchema: Schema[TypeTestClass] = createSchema { context =>
      context.field("stringValue")(_.stringValue).is(nonEmptyString) +
        context.field("intValue")(_.intValue).is(minimalNumberValue(10)) +
        context.field("listValue")(_.listValue).is(noneEmptyCollection and all(nonEmptyString)) +
        context.field("mapValue")(_.mapValue).is(containsKey("test"))
    }

    val pairTestClassSchema: Schema[PairTestClass] = createSchema { context =>
      context.pair("value1")(_.value1)("value2")(_.value2).is(fieldsEqual)
    }

    val optionalTestClassSchema: Schema[OptionalTestClass] = createSchema { context =>
      context.field("stringValue")(_.stringValue).is(ifDefined(nonEmptyString) and isDefined) +
        context.field("intValue")(_.intValue).is(ifDefined(minimalNumberValue(10)))
    }
  }
}
