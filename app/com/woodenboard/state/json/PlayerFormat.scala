package com.woodenboard.state.json

import com.woodenboard.state.{Deck, Player}
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.Writes._
import play.api.libs.json._
import DeckFormat.deckReads
import DeckFormat.deckWrites

/**
 * Defines the format to serialise a player to and from Json.
 */
object PlayerFormat {
  implicit val playerReads : Reads[Player] = (
      (__ \ "id").read[String] and
      (__ \ "name").read[String] and
      (__ \ "decks").read[Map[String, Deck]]
    )(Player.apply _)

  implicit val playerWrites: Writes[Player] = (
      (__ \ "id").write[String] and
      (__ \ "name").write[String] and
      (__ \ "decks").write[Map[String, Deck]]
    )(unlift(Player.unapply))
}
