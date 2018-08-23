package co.tala.nairobijvm.messenger.models.db

case class Message(id: Option[Long], message: String, destination: String, messageType: String)
