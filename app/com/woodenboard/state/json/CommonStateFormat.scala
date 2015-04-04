package com.woodenboard.state.json

import _root_.com.woodenboard.state._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.Writes._
import play.api.libs.json._
import DeckFormat.deckReads
import DeckFormat.deckWrites

/**
 * Format to serialise the common state to and from Json.
 */
object CommonStateFormat {
  val commonStateReads: Reads[CommonState] = (
      (__ \ "decks").read[Map[String, Deck]] and
      (__ \ "data").read[Map[String, String]]
    )(CommonState)

  val commonStateWrites: Writes[CommonState] = (
      (__ \ "decks").write[Map[String, Deck]] and
      (__ \ "data").write[Map[String, String]]
    )(unlift(CommonState.unapply))

  implicit val commonStateFormat: Format[CommonState] = Format(commonStateReads, commonStateWrites)
}
