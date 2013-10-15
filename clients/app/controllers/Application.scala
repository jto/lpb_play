package controllers

import scala.concurrent.Future

import play.api._
import play.api.mvc._
import play.api.libs.json._

import models._

import play.api.libs.concurrent.Execution.Implicits._

object Application extends Controller {

  def client(id: Long) = Action {
   //  val res = Client.get(id).flatMap{ client: Option[Client] =>
	  //   client.map { c =>
	  //   	Compte.byClient(id)
	  //   		.map{ cs => Ok(views.html.index(c, cs)) }
	  //   }.getOrElse(Future.successful(NotFound))
	  // }


	  // syntaxe alternative
	  val res = for(
	  	client <- Client.get(id);
	  	cs     <- Compte.byClient(id)
	  ) yield {
	  	client
	  		.map { c =>  Ok(views.html.index(c, cs)) }
	  		.getOrElse(NotFound)
	  }

	  Async(res)
  }

  def clients = Action {

    val eventuallyClients = Client.all
    val eventuallyComptes = Compte.all

    val result =
	    for(
	    	clients <- eventuallyClients;
	    	comptes <- eventuallyComptes
	    ) yield {
	    	val groupedComptes: Map[Long, Seq[Compte]] = comptes.groupBy(c => c.clientId)
	    	val clientsWithComptes = clients.map{ c => (c, groupedComptes.get(c.id).toSeq.flatten) }
	    	Ok(views.html.clients(clientsWithComptes))
	    }

	  Async(result)
  }

  //Ok(views.html.index("Your new application is ready."))
}