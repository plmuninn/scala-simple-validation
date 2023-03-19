package pl.muninn.simple.validation

import pl.muninn.simple.validation.implicits._

trait TypeImplicits
    extends AnyTypeImplicits
    with CollectionImplicits
    with NumberImplicits
    with OptionImplicits
    with StringImplicits
    with MapImplicits

object TypeImplicits extends TypeImplicits
