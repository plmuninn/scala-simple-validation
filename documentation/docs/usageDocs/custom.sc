import pl.muninn.markdown.Markdown.{*, given}
import pl.muninn.markdown.common.Configuration

def markdown(using Configuration) = md {
  h1("Quick custom validators")
  p{
    m"You can easily create custom validators on fly - it's really helpful during working on new or custom validators"
    br
    codeBlock(
      "scala mdoc",
      """
        | import pl.muninn.simple.validation.all._
        |
        | case class Field(name:String, otherField:String)
        |
        | val schema:Schema[Field] = createSchema { context =>
        |   context.field(_.name)
        |     .custom(code = "contains_test", reason = "Should contains test")(_.contains("test"))
        | }
        |
        | schema.validate(Field("",""))
        | schema.validate(Field("test",""))
        |
        |""".stripMargin
    )
  }
  br
  h1("Complex validation schema")
  p{
    m"You can also easily create custom validation logic. For example, some values should be defined only if other"
    m" value is set to specific value etc."
    br
    codeBlock(
      "scala mdoc",
      """
        | import pl.muninn.simple.validation.all._
        |
        | case class Product(name:String, price:Long)
        | case class Order(totalPrice:Long, products:List[Product])
        |
        | val orderSchema: Schema[Order] = createSchema { context =>
        |    context.field(_.totalPrice).min(1) +
        |      context.field(_.products).notEmpty +
        |      context.custom { order =>
        |         // If total price is equal or above 100 we want all products to be at least 50
        |        if (order.totalPrice >= 100) {
        |          context.field(_.products.map(_.price)).all(min(50L))
        |        } else {
        |         // Don't validate anything - we are good
        |          context.noneValidator
        |        }
        |      }
        |  }
        |
        | orderSchema.validate(Order(10, List.empty))
        | orderSchema.validate(
        |   Order(
        |     60,
        |     List(
        |       Product("My product", 20),
        |       Product("My product", 20),
        |       Product("My product", 20),
        |     )
        |   )
        | )
        | orderSchema.validate(
        |   Order(
        |     100,
        |     List(
        |       Product("My product", 20),
        |       Product("My product", 20),
        |       Product("My product", 20),
        |       Product("My product", 20),
        |       Product("My product", 20),
        |     )
        |   )
        | )
        | orderSchema.validate(
        |   Order(
        |     165,
        |     List(
        |       Product("My product", 55),
        |       Product("My product", 55),
        |       Product("My product", 55),
        |     )
        |   )
        | )
        |
        |""".stripMargin
    )
  }
  br
  h1("Custom validators")
  p {
    m"You can define full own validators and errors"
    br
    codeBlock(
      "scala mdoc",
      """
        | import pl.muninn.simple.validation.all._
        | import pl.muninn.simple.validation.InvalidField
        | import pl.muninn.simple.validation.ValueValidator
        |
        | case class MyError(field:String) extends InvalidField {
        |   override def reason: String = "Because I think this filed is invalid"
        |   override def code: String = "error_code"
        | }
        |
        | val myOwnValidator:ValueValidator[String] = ValueValidator.instance { case (key, value) =>
        |   if (value.contains("not error")) valid else invalid(MyError(key))
        | }
        |
        | val customValidatorSchema:Schema[Field] = createSchema { context =>
        |   context.field(_.name).is(myOwnValidator)
        | }
        |
        | customValidatorSchema.validate(Field("error",""))
        | customValidatorSchema.validate(Field("not error",""))
        |
        |""".stripMargin
    )
  }
}