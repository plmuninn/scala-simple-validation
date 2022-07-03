import os.*

import pl.muninn.markdown.Markdown.*

def generateTemplates(values: Map[os.Path, String]): Unit =
  values.foreach { case (markdownPath, markdown) =>
    pprint.pprintln(s"Saving markdown $markdownPath")
    os.write.over(markdownPath, markdown, createFolders = true)
    pprint.pprintln("Markdown saved:")
    pprint.pprintln(markdown)
  }
