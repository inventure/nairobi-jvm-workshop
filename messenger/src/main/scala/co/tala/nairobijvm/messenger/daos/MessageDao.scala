package co.tala.nairobijvm.messenger.daos

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import co.tala.nairobijvm.messenger.models.db.Message

@Singleton
class MessageDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  (implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  class MessageTable(tag: Tag) extends Table[Message](tag, "message") {
    def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def message: Rep[String] = column[String]("message")

    def destination: Rep[String] = column[String]("destination")

    def messageType: Rep[String] = column[String]("message_type")

    override def * : ProvenShape[Message] =
      (id?, message, destination, messageType) <> (Message.tupled, Message.unapply)
  }

  lazy val table: TableQuery[MessageTable] = TableQuery[MessageTable]

  def insert(message: Message): Future[Long] = {
    db.run((table returning table.map(_.id)) += message)
  }

  def findAll(): Future[Seq[Message]] = {
    db.run(table.result)
  }

  def findById(id: Long): Future[Option[Message]] = {
    db.run(table.filter(_.id === id).result.headOption)
  }
}
