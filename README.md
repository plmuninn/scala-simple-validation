# [Scala simple validation](https://plmuninn.github.io/scala-simple-validation/)

![workflow](https://github.com/plmuninn/scala-simple-validation/actions/workflows/ci.yml/badge.svg) 

[![scala-simple-validation Scala version support](https://index.scala-lang.org/plmuninn/scala-simple-validation/scala-simple-validation/latest.svg)](https://index.scala-lang.org/plmuninn/scala-simple-validation/scala-simple-validation)

[![scala-simple-validation Scala version support](https://index.scala-lang.org/plmuninn/scala-simple-validation/scala-simple-validation/latest-by-scala-version.svg?platform=jvm)](https://index.scala-lang.org/plmuninn/scala-simple-validation/scala-simple-validation)
[![scala-simple-validation Scala version support](https://index.scala-lang.org/plmuninn/scala-simple-validation/scala-simple-validation/latest-by-scala-version.svg?platform=sjs1)](https://index.scala-lang.org/plmuninn/scala-simple-validation/scala-simple-validation)
[![scala-simple-validation Scala version support](https://index.scala-lang.org/plmuninn/scala-simple-validation/scala-simple-validation/latest-by-scala-version.svg?platform=native0.4)](https://index.scala-lang.org/plmuninn/scala-simple-validation/scala-simple-validation)

Library for simple data validation in scala.

Allows you to define schema for validation and then validate data using this schema.

Use cats [Validated](https://typelevel.org/cats/datatypes/validated.html) under the hood.

Provides features like:

* easy way for describing validation schema
* few common validators to use
* simple way of creating your own, custom validators
* designing more complex validation process - where validation depends on some specific value
* allow to reuse validation schemas as validators

For more information - go to [microsite](https://plmuninn.github.io/scala-simple-validation/)
