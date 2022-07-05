
import os.*
import layout.*

private def documentElement(document:DocumentLayout):String =
   s"""
      |  - title: ${document.title}
      |    url: ${document.permalink}""".stripMargin

private def subDocument(mainPath:String, document:DocumentLayout):String =  
   s"""
      |    - title: ${document.title}
      |      url: ${document.permalink}""".stripMargin

private def listOfDocuments(mainPath:String, documents:List[DocumentLayout]):String =
  val top = documentElement(documents.head)
  val menuSection = documents.head.title.toLowerCase
  val submenuElements = documents.tail.map(subDocument(mainPath, _))
  if submenuElements.isEmpty then top else s"$top\n    menu_section: $menuSection\n    nested_options:  ${submenuElements.mkString("\n")}"

def generateMenu(mainPath:String, pages:List[Layout]):Unit =
  pprint.pprintln(s"Generating menu")
  val docs = pages.collect {
    case document:DocumentLayout => document
  }

  val groupdDocuments = docs.groupBy { document =>
    document.permalink.replace(mainPath, "").split("/").head
  }.map {
    case (_, menus) => menus
  }

  val optionLists = groupdDocuments.map(listOfDocuments(mainPath, _))

  val options = s"options:${optionLists.mkString("\n")}"
  pprint.pprintln(s"Saving menu")
  pprint.pprintln(s"$options")
  os.write.over(os.pwd / "source" / "src" / "main" / "resources" / "microsite" / "data" / "menu.yaml", options, createFolders = true)
