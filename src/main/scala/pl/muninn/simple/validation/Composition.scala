package pl.muninn.simple.validation

import pl.muninn.simple.validation.composition._

trait Composition extends AnyTypeComposition with CollectionComposition with NumberComposition with OptionComposition with StringComposition

object Composition
