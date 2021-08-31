import shapeless._
import shapeless.record.Record
import shapeless.Generic
import shapeless.syntax.std.tuple._
import shapeless.syntax.std.product._
import shapeless._, syntax.singleton._, record._

object Users {

  case class Customer(name: String)

  val customer            = Customer("Matt")
  val customerGeneric     = LabelledGeneric[Customer].to(customer)
  val richCustomerGeneric = customerGeneric + ('suburb ->> "String")
}
