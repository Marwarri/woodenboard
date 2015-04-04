package com.woodenboard.state.json

import com.woodenboard.state.{Card, Deck}
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.Writes._
import play.api.libs.json._
import CardFormat.cardReads
import CardFormat.cardWrites

/**
 * Defines the format to serialise a deck to and from Json.
 */
object DeckFormat {
  implicit val deckReads : Reads[Deck] = (
      (__ \ "id").read[String] and
      (__ \ "cards").read[List[Card]]
    )(Deck.apply _)

  implicit val deckWrites: Writes[Deck] = (
      (__ \ "id").write[String] and
      (__ \ "cards").write[List[Card]]
    )(unlift(Deck.unapply))
}
