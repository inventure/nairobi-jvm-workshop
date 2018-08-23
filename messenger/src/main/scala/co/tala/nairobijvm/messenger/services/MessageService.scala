package co.tala.nairobijvm.messenger.services

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

import play.api.libs.json.Json

import co.tala.nairobijvm.messenger.daos.MessageDao
import co.tala.nairobijvm.messenger.models.db.Message
import co.tala.nairobijvm.messenger.models.http.SendMessageRequest
import co.tala.nairobijvm.messenger.utils.NamedLogger

@Singleton
class MessageService @Inject()(messageDao: MessageDao)(implicit ec: ExecutionContext) extends NamedLogger {

  def sendMessage(sendMessageRequest: SendMessageRequest): Future[Long] = {
    logger.info(s"Scheduling message for sending: ${Json.toJson(sendMessageRequest)}")

    val messageToInsert = Message(id = None, message = sendMessageRequest.message,
      destination = sendMessageRequest.destination, messageType = sendMessageRequest.messageType)

    messageDao.insert(messageToInsert)
  }

  def getMessages: Future[Seq[Message]] = messageDao.findAll()

  def getMessage(id: Long): Future[Option[Message]] = messageDao.findById(id)

}
