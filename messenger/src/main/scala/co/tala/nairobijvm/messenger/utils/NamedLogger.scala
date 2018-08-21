package co.tala.nairobijvm.messenger.utils

import play.api.Logger

trait NamedLogger {
  val logger: Logger = Logger(this.getClass)
}
