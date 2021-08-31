import shapeless._
import shapeless.record.Record
import shapeless.Generic
import shapeless.syntax.std.tuple._
import shapeless.syntax.std.product._
import shapeless._, syntax.singleton._, record._

object Users {
  case class MetaEntity(
      additional_notes: String,
      metakey: String
  )

  case class UsersEntity(
      username: String,
      email_address: String
  )

  val users     = LabelledGeneric[UsersEntity]
  val meta      = LabelledGeneric[MetaEntity]
  val usersList = users + ('suburb ->> "String")
}
