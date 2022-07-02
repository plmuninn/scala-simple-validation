//> using scala "3.1"
//> using repository "sonatype:snapshots"
//> using repository "sonatype:release"
//> using lib "pl.muninn::scala-md-tag:1.0.0-SNAPSHOT"
//> using lib "com.lihaoyi::pprint:0.7.3"
//> using lib "com.lihaoyi::os-lib:0.8.1"

import pages.index
import docs.validators
import utils.files.*
import utils.layout.*

import pl.muninn.markdown.common.Configuration.DefaultConfiguration
import pl.muninn.markdown.common.Configuration

given Configuration = DefaultConfiguration().withEscapeLiterals(false).withSafeInserting(false).withTableStrictPrinting(false)

val basePath =  os.pwd / "docs"

val markdowns = Map(
  basePath / "index.md" -> HomeLayout("home", "About", "about", 1).layoutString(index.markdown),
  basePath / "docs" / "validators.md" -> DocumentLayout("Validators", permalink = Some("docs/")).layoutString(validators.markdown)
)

pprint.pprintln("Starting scala-cli - generating files")
generateFiles(markdowns)
pprint.pprintln("Files generated")
