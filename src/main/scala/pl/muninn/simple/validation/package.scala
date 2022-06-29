package pl.muninn.simple

import scala.language.implicitConversions

package object validation {
  object all extends Implicits with Validators with Composition
}
