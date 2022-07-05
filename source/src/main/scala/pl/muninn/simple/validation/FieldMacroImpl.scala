package pl.muninn.simple.validation

import scala.reflect.macros.blackbox

import pl.muninn.simple.validation.model.Validation

class FieldMacroImpl(val c: blackbox.Context) {

  private val DEBUG = false

  import c.universe._

  private def fieldNameSelector(tree: Tree): String = {
    def select(tree: Tree, acc: List[String] = Nil): List[String] =
      tree match {
        case Ident(_: TermName)                                                       => acc
        case Select(This(_), _: TermName)                                             => acc
        case Select(rest, name: TermName) if !tree.symbol.toString.contains("method") => select(rest, name.toString :: acc)
        case Select(rest, _: TermName) if tree.symbol.toString.contains("method")     => select(rest, acc)
        case Apply(Select(rest, TermName("apply")), List(Literal(Constant(name))))    => select(rest, name.toString :: acc)
        case Apply(body, params) =>
          select(body, acc) ::: params.flatMap(param => select(param, acc))
        case Function(params, body) =>
          select(body, acc) ::: params.flatMap(param => select(param, acc))
        case TypeApply(body, params) =>
          select(body, acc) ::: params.flatMap(param => select(param, acc))
        case _ => acc
      }

    val result = select(tree)

    if (DEBUG) {
      println(s"Macro name selector result: ${result.mkString(".")}")
    }

    if (result.isEmpty)
      c.abort(
        pos = c.enclosingPosition,
        msg = s"""
             |Invalid selector given ${tree}.
             |Macro accepts only parameters like `_.field` or `_.first.second` and `_.optional.map(_.second)`.
             |""".stripMargin
      )
    else result.mkString(".")
  }

  def fieldSelector[T: c.WeakTypeTag, R: c.WeakTypeTag](f: c.Expr[T => R]): c.Expr[Validation[R]] = {
    val self = c.prefix
    val name = fieldNameSelector(f.tree)
    val R    = weakTypeOf[R]
    c.Expr[Validation[R]] {
      q"new pl.muninn.simple.validation.model.Validation[$R]($name, $f($self.value))"
    }
  }

  def pairSelector[T: c.WeakTypeTag, R: c.WeakTypeTag](f: c.Expr[T => R])(f2: c.Expr[T => R]): c.Expr[Validation[R]] = {
    val self  = c.prefix
    val name1 = fieldNameSelector(f.tree)
    val name2 = fieldNameSelector(f2.tree)
    val name  = s"$name1 and $name2"
    val R     = weakTypeOf[R]
    c.Expr[Validation[R]] {
      q"new pl.muninn.simple.validation.model.Validation[($R,$R)]($name, ($f($self.value), $f2($self.value)))"
    }
  }
}
