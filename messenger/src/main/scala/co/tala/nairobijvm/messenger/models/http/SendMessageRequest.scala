package co.tala.nairobijvm.messenger.models.http

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.{Format, JsPath}

case class SendMessageRequest(message: String, destination: String, messageType: String)

object SendMessageRequest {
//  implicit val formats: Format[SendMessageRequest] = Json.format[SendMessageRequest]

  implicit val formatsWithValidation: Format[SendMessageRequest] = (
    (JsPath \ "message").format[String] and
      (JsPath \ "destination").format[String](minLength[String](10) keepAnd maxLength[String](12)) and
      (JsPath \ "messageType").format[String]
    ) (SendMessageRequest.apply, unlift(SendMessageRequest.unapply))
}
