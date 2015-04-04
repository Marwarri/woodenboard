package com.woodenboard.state.json

import _root_.com.woodenboard.state._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.Writes._
import play.api.libs.json._
import ActiveTurnFormat._
import PlayerFormat.playerReads
import PlayerFormat.playerWrites
import CommonStateFormat.commonStateFormat

/**
 * Defines the format to serialise a GameState to and from Json.
 */
object GameStateFormat {
  implicit val gameStateReads : Reads[GameState] = (
      (__ \ "gameId").read[String] and
      (__ \ "ruleSet").read[String] and
      (__ \ "state" \ "players").read[Set[Player]] and
      (__ \ "state" \ "common").read[CommonState] and
      (__ \ "active").read[ActiveTurn]
    )(GameState.apply _)

  implicit val gameStateWrites : Writes[GameState] = (
      (__ \ "gameId").write[String] and
      (__ \ "ruleSet").write[String] and
      (__ \ "state" \ "players").write[Set[Player]] and
      (__ \ "state" \ "common").write[CommonState] and
      (__ \ "active").write[ActiveTurn]
    )(unlift(GameState.unapply))
}
