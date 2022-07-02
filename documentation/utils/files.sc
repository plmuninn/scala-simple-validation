import os.*

import pl.muninn.markdown.Markdown.*
import pl.muninn.markdown.common.MarkdownFragment.MarkdownDocument

def generateFiles(values: Map[String, String]): Unit =
  values.foreach { case (path, markdown) =>
    val markdownPath = os.pwd / "docs" / path
    pprint.pprintln(s"Saving markdown $markdownPath")
    os.write.over(markdownPath, markdown, createFolders = true)
    pprint.pprintln("Markdown saved:")
    pprint.pprintln(markdown)
  }
