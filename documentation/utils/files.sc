import pl.muninn.markdown.common.MarkdownFragment.MarkdownDocument
import pl.muninn.markdown.Markdown.*
import os.*

def generateFiles(values: Map[String, MarkdownDocument]): Unit =
  values.foreach {
    case (path, markdown) =>
      val generatedMarkdown = generateUnsafe(markdown)
      val markdownPath = os.pwd / "docs" / path
      pprint.pprintln(s"Saving markdown $markdownPath")
      os.write.over(markdownPath, generatedMarkdown, createFolders = true)
      pprint.pprintln("Markdown saved:")
      pprint.pprintln(generatedMarkdown)
  }

