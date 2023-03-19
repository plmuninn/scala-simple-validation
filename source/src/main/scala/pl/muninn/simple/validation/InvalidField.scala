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
    val reason: String = s"Value must equal $expected. Got $value"

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

  case class MinCountOfSymbols(field: String, expected: Int, found: Int, symbols: List[Char]) extends InvalidField {
    val reason: String = s"Count of symbols must be greater or equal $expected. Got $found"

    val code: String = "min_count_symbols"

    override val metadata: Map[String, String] = Map(
      "expected"         -> expected.toString,
      "count"            -> found.toString,
      "expected_symbols" -> symbols.map(symbol => s"'$symbol'").mkString(";")
    )
  }

  case class MinCountOfDigits(field: String, expected: Int, found: Int) extends InvalidField {
    val reason: String = s"Count of digits must be greater or equal $expected. Got $found"

    val code: String = "min_count_digits"

    override val metadata: Map[String, String] = Map(
      "expected" -> expected.toString,
      "count"    -> found.toString
    )
  }

  case class MinCountOfLowerCases(field: String, expected: Int, found: Int) extends InvalidField {
    val reason: String = s"Count of lower cases must be greater or equal $expected. Got $found"

    val code: String = "min_count_lower_case"

    override val metadata: Map[String, String] = Map(
      "expected" -> expected.toString,
      "count"    -> found.toString
    )
  }

  case class MinCountOfUpperCases(field: String, expected: Int, found: Int) extends InvalidField {
    val reason: String = s"Count of upper cases must be greater or equal $expected. Got $found"

    val code: String = "min_count_upper_case"

    override val metadata: Map[String, String] = Map(
      "expected" -> expected.toString,
      "count"    -> found.toString
    )
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

  case class ValueContains[K](field: String, expected: K) extends InvalidField {
    val reason: String = s"Value should contain $expected"

    val code: String = "value_contains"

    override val metadata: Map[String, String] = Map(
      "expected" -> expected.toString
    )
  }

  case class OneOfValuesContains[K](field: String, expected: Iterable[K]) extends InvalidField {
    val reason: String = s"One of values ${expected.mkString(", ")} should be found"

    val code: String = "one_of_values_contains"

    override val metadata: Map[String, String] = Map(
      "expected" -> expected.mkString(",")
    )
  }

  case class OneOfValuesMissing[K](field: String, expected: Iterable[K]) extends InvalidField {
    val reason: String = s"One of values ${expected.mkString(", ")} is missing"

    val code: String = "one_of_values_missing"

    override val metadata: Map[String, String] = Map(
      "expected" -> expected.mkString(",")
    )
  }
}
