package pl.muninn.simple.validation

import pl.muninn.simple.validation.all._
import pl.muninn.simple.validation.test.{OptionalTestClass, PairTestClass, TypeTestClass}

class ImplicitSuite extends munit.FunSuite {

  trait Suit {
    val typeTestClassSchema: Schema[TypeTestClass] = createSchema { context =>
      context.field("stringValue")(_.stringValue).noneEmptyString +
        context.field("intValue")(_.intValue).min(10) +
        (context.field("listValue")(_.listValue).nonEmpty and all(noneEmptyString)) +
        context.field("mapValue")(_.mapValue).containsKey("test")
    }

    val pairTestClassSchema: Schema[PairTestClass] = createSchema { context =>
      context.pair("value1")(_.value1)("value2")(_.value2).fieldsEqual
    }

    val optionalTestClassSchema: Schema[OptionalTestClass] = createSchema { context =>
      (context.field("stringValue")(_.stringValue).isDefined and ifDefined(noneEmptyString)) +
        context.field("intValue")(_.intValue).ifDefined(minimalNumberValue(10))
    }
  }
}
