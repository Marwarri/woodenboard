package com.woodenboard.state.json

import _root_.com.woodenboard.state._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.Writes._
import play.api.libs.json._
import GameOptionFormat.gameOptionReads
import GameOptionFormat.gameOptionWrites

/**
 * Defines the format to serialise an active turn to and from Json.
 */
object ActiveTurnFormat {
  implicit val activeTurnReads : Reads[ActiveTurn] = (
      (__ \ "player").read[String] and
      (__ \ "phase").read[String] and
      (__ \ "options").read[Set[GameOption]] and
      (__ \ "turnData").read[Map[String, String]]
    )(ActiveTurn)

  implicit val activeTurnWrites: Writes[ActiveTurn] = (
      (__ \ "player").write[String] and
      (__ \ "phase").write[String] and
      (__ \ "options").write[Set[GameOption]] and
      (__ \ "turnData").write[Map[String, String]]
    )(unlift(ActiveTurn.unapply))
}
