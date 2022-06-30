package pl.muninn.simple.validation

import scala.annotation.tailrec
import scala.reflect.macros.blackbox

import pl.muninn.simple.validation.model.Validation

object FieldMacro {

  def fieldSelector[T: c.WeakTypeTag, R: c.WeakTypeTag](c: blackbox.Context)(f: c.Expr[T => R]): c.Expr[Validation[R]] = {
    import c.universe._

    def selectorPath(tree: Tree): List[String] = {
      @tailrec
      def select(tree: Tree, acc: List[String] = Nil): List[String] =
        tree match {
          case Ident(_: TermName)                                                    => acc
          case Select(This(_), _: TermName)                                          => acc
          case Select(rest, name: TermName)                                          => select(rest, name.toString :: acc)
          case Apply(Select(rest, TermName("apply")), List(Literal(Constant(name)))) => select(rest, name.toString :: acc)
          case Apply(Select(rest, TermName("apply")), List(Literal(Constant(name)))) => select(rest, name.toString :: acc)
          case t => c.abort(c.enclosingPosition, s"Invalid selector: $t - ${show(t)}")
        }

      select(tree)
    }

    val self = c.prefix

    val name = selectorPath(f.tree.children.tail.head).mkString(".")
    println(name)
    val R = weakTypeOf[R]
    c.Expr[Validation[R]] {
      q"new pl.muninn.simple.validation.model.Validation[$R]($name, $f($self.value))"
    }
  }

}
