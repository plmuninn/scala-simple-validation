package pl.muninn.simple.validation

trait InvalidField {
  def field: String
  def reason: String
  def code: String
  def metadata: Map[String, String] = Map.empty
}

object InvalidField {

  def custom(filedNameFailed: String, reasonOfError: String, codeOfError: String, metaInfo: Map[String, String] = Map.empty): InvalidField =
    new InvalidField {
      val field: String = filedNameFailed

      val reason: String = reasonOfError

      val code: String = codeOfError

      override val metadata: Map[String, String] = metaInfo
    }

  case class EqualValue[T](field: String, expected: T, value: T) extends InvalidField {
    val reason: String = s"Value not equal $expected. Got $value"

    val code: String = "equal_field"

    override val metadata: Map[String, String] = Map(
      "expected" -> expected.toString,
      "value"    -> value.toString
    )
  }

  case class EmptyField(field: String) extends InvalidField {
    val reason: String = "Non empty value required"
    val code: String   = "empty_field"
  }

  case class ExpectedEmpty(field: String) extends InvalidField {
    val reason: String = "Value must be empty"
    val code: String   = "empty_expected"
  }

  case class NotEmail(field: String) extends InvalidField {
    val reason: String = "Value must be a valid email"
    val code: String   = "email_field"
  }

  case class NotComplexPassword(field: String) extends InvalidField {
    val reason: String =
      "Password needs to be at least 8 characters long and contains 1 number and 1 special symbol and big and small letters"
    val code: String = "password_complexity"
  }

  case class FieldsNotEqual(field: String) extends InvalidField {
    val reason: String = s"Values $field are not equal"
    val code: String   = "fields_not_equal"
  }

  case class MinimalValue[E, R](field: String, expected: E, value: R) extends InvalidField {
    val reason: String = s"Value must be greater or equal $expected. Got $value"
    val code: String   = "minimal_value"

    override val metadata: Map[String, String] = Map(
      "expected" -> expected.toString,
      "value"    -> value.toString
    )
  }

  case class MaximalValue[E, R](field: String, expected: E, value: R) extends InvalidField {
    val reason: String = s"Value must be lower or equal $expected. Got $value"
    val code: String   = "maximal_value"

    override val metadata: Map[String, String] = Map(
      "expected" -> expected.toString,
      "value"    -> value.toString
    )
  }

  case class ExpectedValue[E, R](field: String, expected: E, value: R) extends InvalidField {
    val reason: String = s"Value must be equal $expected. Got $value"
    val code: String   = "expected_value"

    override val metadata: Map[String, String] = Map(
      "expected" -> expected.toString,
      "value"    -> value.toString
    )
  }

  case class MinimalLength[E, R](field: String, expected: E, value: R) extends InvalidField {
    val reason: String = s"Length must be greater or equal $expected. Got $value"
    val code: String   = "minimal_length"

    override val metadata: Map[String, String] = Map(
      "expected" -> expected.toString,
      "value"    -> value.toString
    )
  }

  case class MaximalLength[E, R](field: String, expected: E, value: R) extends InvalidField {
    val reason: String = s"Length must be lower or equal $expected. Got $value"
    val code: String   = "maximal_length"

    override val metadata: Map[String, String] = Map(
      "expected" -> expected.toString,
      "value"    -> value.toString
    )
  }

  case class ExpectedLength[E, R](field: String, expected: E, value: R) extends InvalidField {
    val reason: String = s"Length must be equal $expected. Got $value"
    val code: String   = "expected_length"

    override val metadata: Map[String, String] = Map(
      "expected" -> expected.toString,
      "value"    -> value.toString
    )
  }

  case class KeyMissing[K](field: String, expected: K) extends InvalidField {
    val reason: String = s"Key $expected is missing"

    val code: String = "key_missing"

    override val metadata: Map[String, String] = Map(
      "expected" -> expected.toString
    )
  }

  case class KeysMissing[K](field: String, expected: Iterable[K]) extends InvalidField {
    val reason: String = s"Keys ${expected.mkString(", ")} are missing"

    val code: String = "keys_missing"

    override val metadata: Map[String, String] = Map(
      "expected" -> expected.mkString(";")
    )
  }
}
