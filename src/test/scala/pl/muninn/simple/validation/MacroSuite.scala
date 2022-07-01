package pl.muninn.simple.validation

import cats.data.{Validated, ValidatedNec}

import pl.muninn.simple.validation.all._
import pl.muninn.simple.validation.test.{CombinedClass, ListCombinedClass, OptionalTestClass, PairTestClass, SimpleCombinedClass, TypeTestClass}

class MacroSuite extends munit.FunSuite {

  trait Suite {
    val optionalTestClassSchema: Schema[OptionalTestClass] = createSchema { context =>
      context.field(_.stringValue).isDefined +
        context.field(_.intValue).isDefined
    }

    val simpleCombinedClassSchema: Schema[SimpleCombinedClass] = createSchema { context =>
      context.field(_.innerClass.stringValue).isDefined +
        context.field(_.innerClass.intValue).isDefined
    }

    val combinedClassSchema: Schema[CombinedClass] = createSchema { context =>
      context.field(_.innerClass.flatMap(_.stringValue)).isDefined +
        context.field(_.innerClass.flatMap(_.intValue)).isDefined
    }

    val listCombinedClassSchema: Schema[ListCombinedClass] = createSchema { context =>
      context.field(_.values.flatMap(_.innerClass).map(_.stringValue)).nonEmpty
    }

    val pairTestClassSchema: Schema[PairTestClass] = createSchema { context =>
      context.pair(_.value1)(_.value2).fieldsEqual
    }
  }

  test("macro for simple test class should generate field names properly") {
    new Suite {
      val result: ValidatedNec[InvalidField, Unit] = optionalTestClassSchema.validate(OptionalTestClass(None, None))
      assert(result.isInvalid)
      result match {
        case Validated.Valid(_) => fail("Results should be invalid")
        case Validated.Invalid(e) =>
          assertEquals(e.length, 2L)
          assertEquals(e.map(_.field).iterator.toList, List("stringValue", "intValue"))
      }
    }
  }

  test("macro for nested field should generate names properly") {
    new Suite {
      val result: ValidatedNec[InvalidField, Unit] = simpleCombinedClassSchema.validate(SimpleCombinedClass(OptionalTestClass(None, None)))
      assert(result.isInvalid)
      result match {
        case Validated.Valid(_) => fail("Results should be invalid")
        case Validated.Invalid(e) =>
          assertEquals(e.length, 2L)
          assertEquals(e.map(_.field).iterator.toList, List("innerClass.stringValue", "innerClass.intValue"))
      }
    }
  }

  test("macro for nested field with function should generate name properly") {
    new Suite {
      val result: ValidatedNec[InvalidField, Unit] = combinedClassSchema.validate(CombinedClass(None))
      assert(result.isInvalid)
      result match {
        case Validated.Valid(_) => fail("Results should be invalid")
        case Validated.Invalid(e) =>
          assertEquals(e.length, 2L)
          assertEquals(e.map(_.field).iterator.toList, List("innerClass.stringValue", "innerClass.intValue"))
      }
    }
  }

  test("macro for nested field in list with function should generate name properly") {
    new Suite {
      val result: ValidatedNec[InvalidField, Unit] = listCombinedClassSchema.validate(ListCombinedClass(List()))
      assert(result.isInvalid)
      result match {
        case Validated.Valid(_) => fail("Results should be invalid")
        case Validated.Invalid(e) =>
          assertEquals(e.length, 1L)
          assertEquals(e.map(_.field).iterator.toList, List("values.innerClass.stringValue"))
      }
    }
  }

  test("macro for pair names should generate properly") {
    new Suite {
      val result: ValidatedNec[InvalidField, Unit] = pairTestClassSchema.validate(PairTestClass("test", "test2"))
      assert(result.isInvalid)
      result match {
        case Validated.Valid(_) => fail("Results should be invalid")
        case Validated.Invalid(e) =>
          assertEquals(e.length, 1L)
          assertEquals(e.map(_.field).iterator.toList, List("value1 and value2"))
      }
    }
  }
}
