package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._

import views._
import models._

/**
 * Manage a database of computers
 */
object Application extends Controller {

  def list = Action { implicit request =>
    val cs: Seq[Contact] = Contact.all
    Ok(Json.toJson(cs))
  }

  def get(id: Long) = Action {
    Contact.find(id).map { c =>
      Ok(Json.toJson(c))
    }.getOrElse(NotFound)
  }

  def update(id: Long) = Action(parse.urlFormEncoded) { request =>
    NotImplemented
  }

  def create = Action(parse.json){ request =>
    val json: JsValue = request.body
    val maybeContact = Json.fromJson[Contact](json)
    maybeContact.fold(
      err => BadRequest(JsError.toFlatJson(err)),
      contact => Ok(Json.obj("id" -> Contact.add(contact)))
    )
  }

  def delete(id: Long) = Action {
    Contact.delete(id)
    Ok
  }

}

