import pl.muninn.markdown.Markdown.{*, given}

val markdown = md {
  h1("Test tile")
  p {
    m"test file"
  }
}