package co.tala.nairobijvm.messenger.controllers

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

import play.api.Configuration
import play.api.libs.json.{JsError, JsPath, JsValue, JsonValidationError}
import play.api.mvc.{AbstractController, Action, ControllerComponents}

import co.tala.nairobijvm.messenger.configs.GeneralAppConfig
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

}
