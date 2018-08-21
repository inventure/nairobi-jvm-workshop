package co.tala.nairobijvm.messenger.controllers

import javax.inject._

import play.api._
import play.api.mvc._

import co.tala.nairobijvm.messenger.utils.NamedLogger

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class MessengerController @Inject()(cc: ControllerComponents, configuration: Configuration) extends AbstractController(cc) with NamedLogger {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Logger.info("Default Messenger action has been called!")
    logger.info("Default Messenger action has been called!")
    Ok(s"Hello World. Our app is called: ${configuration.get[String]("general.name")}")
  }
}
