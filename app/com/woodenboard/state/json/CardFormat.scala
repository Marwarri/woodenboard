package com.woodenboard.state.json

import _root_.com.woodenboard.state._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.Writes._
import play.api.libs.json._

/**
 * Defines the format to serialise a card to and from Json.
 */
object CardFormat {
  implicit val cardReads : Reads[Card] = (
      (__ \ "id").read[String] and
      (__ \ "type").read[String]
    )(Card.apply _)

  implicit val cardWrites: Writes[Card] = (
      (__ \ "id").write[String] and
      (__ \ "type").write[String]
    )(unlift(Card.unapply))
}
