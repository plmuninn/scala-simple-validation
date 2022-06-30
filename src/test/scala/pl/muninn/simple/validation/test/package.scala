package pl.muninn.simple.validation

package object test {
  case class TypeTestClass(stringValue: String, intValue: Int, listValue: List[String], mapValue: Map[String, String])
  case class PairTestClass(value1: String, value2: String)
  case class OptionalTestClass(stringValue: Option[String], intValue: Option[Int])
  case class CombinedClass(innerClass: Option[OptionalTestClass])
}
