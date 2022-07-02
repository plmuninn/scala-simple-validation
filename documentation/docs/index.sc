import pl.muninn.markdown.Markdown.{*, given}

val pageMarkdown = md {
  h1("Test tile")
  p {
    m"test file"
  }
}

def markdown =
  s"""---
     |layout: home
     |title:  "Home"
     |section: "home"
     |position: 1
     |---
     |${generateUnsafe(pageMarkdown)}
     |""".stripMargin
