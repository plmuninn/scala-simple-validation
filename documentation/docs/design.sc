import pl.muninn.markdown.Markdown.{*, given}

def markdown(using MarkdownConfig) = md {
  h1("Design")
  p{
    m"Library is design to be quite simple to understand. It's build from few simple components:"
    br
    ul{
      li(code("ValidationSchemaContext"))
      li(code("ValidationSchema"))
      li(code("Field"))
      li(code("FieldValidator"))
      li(code("ValueValidator"))
      li(code("InvalidField"))
    }
    br
    p {
      m"Composition of schema design:"
      br
      br
      img(
        "Schema design",
        "https://mermaid.ink/img/pako:eNqVk7FugzAQhl_F8hSkZMDtxFCpalR165AoQ-MMJ3wUq2AjY5pWhHevIaACJQllML6f-787n3BJQy2QBjRK9DGMwViyXXNF3LMJY0zhSSuLX3a_g0QKsFKrgX4gq9XD6QWTLCdWE4GRVHhqvYuxyeuTa2evCEiVl92mGrdQi02p1xr_LDER_r55HS6mbo-6TWWD1LO7SXm0FsKYfJ771CZvDbtO8BfD2Osh2DwEu4L4rdNMY6RdGsuIP2Fl86z-39G6bwX2jj-MvduUZupDF5tDYbd6ufs_ZaKX-2lKvdIlTdGkIIW7D2WtcGrdX4WcBm4rwHxwylXl8qCwevOtQhpYU-CSFpmD4VrCu4GUBhEkuVMzUG9ad3H1AwmhM3U?type=png)](https://mermaid.live/edit#pako:eNqVk7FugzAQhl_F8hSkZMDtxFCpalR165AoQ-MMJ3wUq2AjY5pWhHevIaACJQllML6f-787n3BJQy2QBjRK9DGMwViyXXNF3LMJY0zhSSuLX3a_g0QKsFKrgX4gq9XD6QWTLCdWE4GRVHhqvYuxyeuTa2evCEiVl92mGrdQi02p1xr_LDER_r55HS6mbo-6TWWD1LO7SXm0FsKYfJ771CZvDbtO8BfD2Osh2DwEu4L4rdNMY6RdGsuIP2Fl86z-39G6bwX2jj-MvduUZupDF5tDYbd6ufs_ZaKX-2lKvdIlTdGkIIW7D2WtcGrdX4WcBm4rwHxwylXl8qCwevOtQhpYU-CSFpmD4VrCu4GUBhEkuVMzUG9ad3H1AwmhM3U",
        "Schema design"
      )
    }
    br
    p {
      m"Result of schema looks like:"
      br
      codeBlock {
        "ValidationSchema[T] => NonEmptyList(T => Field[T] => NotEmptyList[FieldValidator]) => Combine => validateFn(T) => ValidationResult"
      }
    }
  }
}