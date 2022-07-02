//> using scala "3.1"
//> using repository "sonatype:snapshots"
//> using repository "sonatype:release"
//> using lib "pl.muninn::scala-md-tag:1.0.0-SNAPSHOT"
//> using lib "com.lihaoyi::pprint:0.7.3"
//> using lib "com.lihaoyi::os-lib:0.8.1"

import pages.index
import utils.files.*
import utils.layout.HomeLayout

val markdowns = Map(
  "index.md" -> HomeLayout("home", "About", "about", 1).layoutString(index.markdown)
)

pprint.pprintln("Starting scala-cli - generating files")
generateFiles(markdowns)
pprint.pprintln("Files generated")
