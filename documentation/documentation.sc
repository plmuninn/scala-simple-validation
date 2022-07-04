//> using scala "3.1"
//> using repository "sonatype:snapshots"
//> using repository "sonatype:release"
//> using lib "pl.muninn::scala-md-tag:1.0.0-SNAPSHOT"
//> using lib "com.lihaoyi::pprint:0.7.3"
//> using lib "com.lihaoyi::os-lib:0.8.1"

import docs.*
import docs.validatorsTypes.*
import pages.index
import utils.files.*
import utils.layout.*
import utils.menu.*

import pl.muninn.markdown.common.Configuration
import pl.muninn.markdown.common.Configuration.DefaultConfiguration

given Configuration = DefaultConfiguration().withEscapeLiterals(false).withSafeInserting(false).withTableStrictPrinting(false)

val basePath = os.pwd / "docs"

val markdowns = List(
  (basePath / "index.md", HomeLayout("home", "Quickstart", "quickstart", 1), index.markdown),
  (basePath / "docs" / "about.md", DocumentLayout("About", "docs/"), about.markdown),
  (basePath / "docs" / "installation.md", DocumentLayout("Install", "docs/install/"), install.markdown),
  (basePath / "docs" / "validators.md", DocumentLayout("Validators", "docs/validators/"), validators.markdown),
  (basePath / "docs" / "validators" / "any.md", DocumentLayout("Any type validators", "docs/validators/any/"), any.markdown),
  (basePath / "docs" / "validators" / "option.md", DocumentLayout("Option validators", "docs/validators/option/"), option.markdown),
  (basePath / "docs" / "validators" / "string.md", DocumentLayout("String validators", "docs/validators/string/"), string.markdown),
  (basePath / "docs" / "validators" / "number.md", DocumentLayout("Number validators", "docs/validators/number/"), number.markdown),
  (basePath / "docs" / "validators" / "collection.md", DocumentLayout("Collection validators", "docs/validators/collection/"), collection.markdown),
  (basePath / "docs" / "validators" / "map.md", DocumentLayout("Map validators", "docs/validators/map/"), map.markdown),
  (basePath / "docs" / "errors.md", DocumentLayout("Errors", "docs/errors/"), errors.markdown)
)

pprint.pprintln("Starting scala-cli - generating files")
generateTemplates(markdowns.map { case (path, layout, markdown) =>
  path -> layout.layoutString(markdown)
}.toMap)
generateMenu(
  "docs/",
  markdowns.map { case (_, layout, _) =>
    layout
  }
)
pprint.pprintln("Files generated")
