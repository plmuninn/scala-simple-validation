import pl.muninn.markdown.Markdown.{*, given}

def markdown =
  md {
    h1("Test tile")
    p {
      m"test file"
    }
  }
