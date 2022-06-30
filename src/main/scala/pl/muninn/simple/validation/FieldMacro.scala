package pl.muninn.simple.validation

import scala.annotation.tailrec
import scala.reflect.macros.blackbox

import pl.muninn.simple.validation.model.Validation

object FieldMacro {

  def fieldSelector[T: c.WeakTypeTag, R: c.WeakTypeTag](c: blackbox.Context)(f: c.Expr[T => R]): c.Expr[Validation[R]] = {
    import c.universe._

    def selectorPath(tree: Tree): List[String] = {
      def select(tree: Tree, acc: List[String] = Nil): List[String] =
        tree match {
          case Ident(_: TermName)                                                       => acc
          case Select(This(_), _: TermName)                                             => acc
          case Select(rest, name: TermName) if !tree.symbol.toString.contains("method") => select(rest, name.toString :: acc)
          case Select(rest, _: TermName) if tree.symbol.toString.contains("method")     => select(rest, acc)
          case Apply(Select(rest, TermName("apply")), List(Literal(Constant(name))))    => select(rest, name.toString :: acc)
          case Apply(body, params) =>
            params.flatMap(param => select(param, acc)) ++ select(body, acc)
          case Function(params, body) =>
            params.flatMap(param => select(param, acc)) ++ select(body, acc)
          case TypeApply(body, params) =>
            params.flatMap(param => select(param, acc)) ++ select(body, acc)
          case _ => acc
        }

      val result = select(tree)
      if (result.isEmpty) c.abort(c.enclosingPosition, s"Invalid selector: $tree") else result.reverse
    }

    val self = c.prefix

    val name = selectorPath(f.tree).mkString(".")
    println(name)
    val R = weakTypeOf[R]
    c.Expr[Validation[R]] {
      q"new pl.muninn.simple.validation.model.Validation[$R]($name, $f($self.value))"
    }
  }

}
