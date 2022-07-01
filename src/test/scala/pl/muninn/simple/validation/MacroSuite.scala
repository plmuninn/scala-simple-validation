package pl.muninn.simple.validation

import pl.muninn.simple.validation.all._
import pl.muninn.simple.validation.test.{CombinedClass, ListCombinedClass, OptionalTestClass, PairTestClass, SimpleCombinedClass}

class MacroSuite extends munit.FunSuite {

  trait Suite {
    val optionalTestClassSchema: Schema[OptionalTestClass] = createSchema { context =>
      context.field(_.stringValue).isDefined
    }

    val combinedClassSchema: Schema[CombinedClass] = createSchema { context =>
      context.field(_.innerClass.flatMap(_.stringValue)).isDefined
    }

    val simpleCombinedClassSchema: Schema[SimpleCombinedClass] = createSchema { context =>
      context.field(_.innerClass.stringValue).isDefined
    }

    val listCombinedClassSchema: Schema[ListCombinedClass] = createSchema { context =>
      context.field(_.values.flatMap(_.innerClass).map(_.stringValue)).nonEmpty
    }

    val pairTestClassSchema: Schema[PairTestClass] = createSchema { context =>
      context.pair(_.value1)(_.value2).fieldsEqual
    }
  }
}
