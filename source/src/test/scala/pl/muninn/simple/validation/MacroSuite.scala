package pl.muninn.simple.validation

import cats.data.Validated

import pl.muninn.simple.validation.test._

class MacroSuite extends munit.FunSuite {

  trait Suite {
    val optionalTestClassSchema: ValidationSchema[OptionalTestClass] = createSchema { context =>
      context.field(_.stringValue).notEmpty +
        context.field(_.intValue).notEmpty
    }

    val simpleCombinedClassSchema: ValidationSchema[SimpleCombinedClass] = createSchema { context =>
      context.field(_.innerClass.stringValue).notEmpty +
        context.field(_.innerClass.intValue).notEmpty
    }

    val combinedClassSchema: ValidationSchema[CombinedClass] = createSchema { context =>
      context.field(_.innerClass.flatMap(_.stringValue)).notEmpty +
        context.field(_.innerClass.flatMap(_.intValue)).notEmpty
    }

    val listCombinedClassSchema: ValidationSchema[ListCombinedClass] = createSchema { context =>
      context.field(_.values.flatMap(_.innerClass).map(_.stringValue)).notEmpty
    }

    val pairTestClassSchema: ValidationSchema[PairTestClass] = createSchema { context =>
      context.pair(_.value1)(_.value2).fieldsEqual
    }
  }

  test("macro for simple test class should generate field names properly") {
    new Suite {
      val result: ValidationResult = optionalTestClassSchema.validate(OptionalTestClass(None, None))
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
      val result: ValidationResult = simpleCombinedClassSchema.validate(SimpleCombinedClass(OptionalTestClass(None, None)))
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
      val result: ValidationResult = combinedClassSchema.validate(CombinedClass(None))
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
      val result: ValidationResult = listCombinedClassSchema.validate(ListCombinedClass(List()))
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
      val result: ValidationResult = pairTestClassSchema.validate(PairTestClass("test", "test2"))
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
