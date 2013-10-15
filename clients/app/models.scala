package models

import scala.concurrent.Future

case class Client(id: Long, name: String)
case class Compte(id: Long, clientId: Long, solde: BigDecimal)

object Client {

	private val clients = Map(
		1L -> Client(1L, "toto"),
		2L -> Client(2L, "foobar"),
		3L -> Client(3L, "foobar"))

	def get(id: Long): Future[Option[Client]] = {
		// simulate a WS call
		Future.successful(clients.get(id))
	}

	def all: Future[Seq[Client]] = {
		// simulate a WS call
		Future.successful(clients.values.toSeq)
	}

}

object Compte {

	private val comptes = Map(
		1L -> Seq(Compte(1L, 1L, BigDecimal("134"))),
		2L -> Seq(Compte(2L, 2L, BigDecimal("23.91")),
					Compte(3L, 2L, BigDecimal("-2344.87"))))

	def byClient(id: Long): Future[Seq[Compte]] = {
		// simulate a WS call
		Future.successful(comptes.get(id).toSeq.flatten)
	}

	def all: Future[Seq[Compte]] = {
		// simulate a WS call
		Future.successful(comptes.values.toSeq.flatten)
	}

}