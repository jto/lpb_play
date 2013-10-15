package models

case class Contact(firstname: String, lastname: String, email: Option[String], phone: Option[String])

object Contact {
  import play.api.libs.json._
  import play.api.libs.functional.syntax._
  import Reads._

  implicit val contactW = Json.writes[Contact]
  implicit val contactR = (
    (__ \ "firstname").read[String] and
    (__ \ "lastname").read[String] and
    (__ \ "email").readNullable(email) and // test le format de email, c'est un email Optionel
    (__ \ "phone").readNullable(pattern("""[0-9]{2}\.[0-9]{2}\.[0-9]{2}\.[0-9]{2}\.[0-9]{2}""".r))
  )(Contact.apply _)
  // ^^^^^^^^^^^^^^
  // ||||||||||||||
  // ici on a besoin d'une function de type (String, String, Option[String], Option[String]) => Contact
  // on pourrait ecrire (f, l, e, p) => Contact(f, l, e, p)
  // l'utilisation du mot cle "case" sur la declaration de la classe met automatiquement a disposition
  // une methode "apply" qui a exactement cette signature. Le "_" permet de faire une "application partielle" de la methode.
  // Cela transform la methode en une function.

  private val DB = scala.collection.mutable.Map(
    1L -> Contact("Julien", "Tournay", Some("jto@zengularity.com"), None),
    2L -> Contact("Toto", "toto", Some("toto@zengularity.com"), Some("5"))
  )

  def find(id: Long): Option[Contact] = DB.get(id)
  def all: Seq[Contact] = DB.values.toSeq
  def delete(id: Long): Unit = DB -= id
  def add(c: Contact) = {
    val id = DB.size + 1L
    DB += (id -> c)
    id
  }
}

