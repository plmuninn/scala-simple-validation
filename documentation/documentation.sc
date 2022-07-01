//> using scala "3.1"
//> using repository "sonatype:snapshots"
//> using repository "sonatype:release"
//> using lib "pl.muninn::scala-md-tag:1.0.0-SNAPSHOT"
//> using lib "com.lihaoyi::pprint:0.7.3"
//> using lib "com.lihaoyi::os-lib:0.8.1"

import docs.readme
import utils.files.*

val pages = Map(
  "readme.md" -> readme.markdown
)

pprint.pprintln("Starting scala-cli - generating files")
generateFiles(pages)
pprint.pprintln("Files generated")
