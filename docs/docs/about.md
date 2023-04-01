---
layout: docs
title: "About"
permalink: docs/
---
# About

`scala-simple-validation` was designed to allow in simple way validate case classes and other data structures. It provides:
1. easy way for describing validation schema
2. few common validators to use
3. simple way of creating your own, custom validators
4. designing more complex validation process - where validation depends on some specific value
5. allow to reuse validation schemas as validators

Library was designed in a way to be easy to use and quite elastic. It was created because using cats [Validated](https://typelevel.org/cats/datatypes/validated.html) was really repetitive and other libraries are too \"complicated\" in my opinion. I wanted to create something simple and easy to understand.

# Other validation libraries

They are other validation libraries that should mention here - some of them were inspiration for this one:
* [accord](http://wix.github.io/accord/)
* [dupin](https://github.com/yakivy/dupin)
* [octopus](https://github.com/krzemin/octopus)
* [fields](https://jap-company.github.io/fields/)

# Dependencies

Libraray is build using [cats](https://typelevel.org/cats/) in version **@CATS_VERSION@**
