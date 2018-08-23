package co.tala.nairobijvm.messenger.services

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

import play.api.libs.json.Json

import co.tala.nairobijvm.messenger.models.http.SendMessageRequest
import co.tala.nairobijvm.messenger.utils.NamedLogger

@Singleton
class MessageService @Inject()()(implicit ec: ExecutionContext) extends NamedLogger {

  def sendMessage(sendMessageRequest: SendMessageRequest): Future[Unit] = {
    logger.info(s"Scheduling message for sending: ${Json.toJson(sendMessageRequest)}")
    Future.unit
  }

}
