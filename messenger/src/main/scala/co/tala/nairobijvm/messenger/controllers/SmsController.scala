package co.tala.nairobijvm.messenger.controllers

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

import play.api.Configuration
import play.api.libs.json._
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import co.tala.nairobijvm.messenger.configs.GeneralAppConfig
import co.tala.nairobijvm.messenger.models.db.Message
import co.tala.nairobijvm.messenger.models.http.SendMessageRequest
import co.tala.nairobijvm.messenger.services.MessageService
import co.tala.nairobijvm.messenger.utils.NamedLogger

@Singleton
class SmsController @Inject()(
    cc: ControllerComponents,
    configuration: Configuration,
    messageService: MessageService
)(implicit ec: ExecutionContext) extends AbstractController(cc) with NamedLogger {
  val generalAppConfig: GeneralAppConfig = configuration.get[GeneralAppConfig](GeneralAppConfig.configPath)

  implicit val messageWrites: OWrites[Message] = Json.writes[Message]

  def send: Action[JsValue] = Action.async(parse.json) { implicit request =>
    val requestBody: JsValue = request.body
    logger.info(s"Received send message request with $requestBody")

    requestBody.validate[SendMessageRequest].fold(
      (error: Seq[(JsPath, Seq[JsonValidationError])]) => {
        logger.warn("Invalid sendMessageRequest payload")
        Future.successful(BadRequest(JsError.toJson(error)))
      },
      (sendMessageRequest: SendMessageRequest) => {
        logger.info(s"Received  valid sendMessageRequest")
        messageService.sendMessage(sendMessageRequest).map(_ => Accepted)
      }
    )
  }

  def getMessage(id: Long): Action[AnyContent] = Action.async {
    logger.info(s"Fetching message for ID $id")
    messageService.getMessage(id).map { maybeMessage =>
      maybeMessage.map { message =>
        Ok(Json.toJson(message))
      }.getOrElse {
        logger.info(s"Unable to find message with ID $id")
        NotFound
      }
    }
  }

  def getMessages: Action[AnyContent] = Action.async {
    logger.info(s"Fetching all messages")
    messageService.getMessages.map(a => Ok(Json.toJson(a)))
  }

}
