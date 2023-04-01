package pl.muninn.simple.validation.model

/** Represent validation failure
  */
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
}
