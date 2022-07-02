//> using scala "3.1"

import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.MarkdownFragment.MarkdownDocument

trait Layout {
    def layoutString(document:MarkdownDocument):String
}

case class Page(title:String, section:String, position:Int) extends Layout:
    lazy val layoutDescription:String =
        List(
            Some("---"),
            Some("layout: page"),
            Some(s"title: \"$title\""),
            Some(s"section: \"$section\""),
            Some(s"position: $position"),
            Some("---")
        ).flatten.mkString("\n")
        
    override def layoutString(document:MarkdownDocument):String =  s"$layoutDescription\n${generateUnsafe(document)}"

case class Document(title:String, section:Option[String]) extends Layout:
    lazy val layoutDescription:String =
        List(
            Option("---"),
            Option("layout: docs"),
            Option(s"title: \"$title\""),
            section.map(s => s"section: \"$s\""),
            Option("---")
        ).flatten.mkString("\n")

    override def layoutString(document:MarkdownDocument):String = s"$layoutDescription\n${generateUnsafe(document)}"

case class HomeLayout(layout:String, title:String, section:String, position:Int) extends Layout:
    lazy val layoutDescription: String =
      List(
        "---",
        s"layout: $layout",
        s"title: $title",
        s"section: $section",
        s"position: $position",
        "---"
      ).mkString("\n")

    override def layoutString(document:MarkdownDocument):String = s"$layoutDescription\n${generateUnsafe(document)}"