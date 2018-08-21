package co.tala.nairobijvm.messenger.controllers

import javax.inject.{Inject, Singleton}

import scala.concurrent.Future

import play.api.Configuration
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import co.tala.nairobijvm.messenger.configs.GeneralAppConfig
import co.tala.nairobijvm.messenger.utils.NamedLogger

@Singleton
class SmsController  @Inject()(cc: ControllerComponents, configuration: Configuration) extends AbstractController(cc) with NamedLogger {
  val generalAppConfig: GeneralAppConfig = configuration.get[GeneralAppConfig](GeneralAppConfig.configPath)

  def send(message: String): Action[AnyContent] = Action.async {
    Future.successful {
      logger.info(s"Received message: $message in ${generalAppConfig.name}")
      Ok
    }
  }
}
