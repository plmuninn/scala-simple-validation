//> using scala "3.1"

import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.MarkdownFragment.MarkdownDocument

sealed trait Layout {
    def layoutString(document:MarkdownDocument):String
}

case class PageLayout(title:String, section:String, position:Int) extends Layout:
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

case class DocumentLayout(title:String, permalink:String, section:Option[String] = None) extends Layout:
    lazy val layoutDescription:String =
        List(
            Option("---"),
            Option("layout: docs"),
            Option(s"title: \"$title\""),
            section.map(s => s"section: \"$s\""),
            Option(s"permalink: $permalink"),
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