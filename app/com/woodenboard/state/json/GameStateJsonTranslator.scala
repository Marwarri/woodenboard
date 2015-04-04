package com.woodenboard.state.json

import _root_.com.woodenboard.state._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import GameStateFormat._

/**
 * Translates a GameState to and from a Json representation.
 */
trait GameStateJsonTranslator {

  def fromString(json: String): GameState = {
    val jsonState = Json.parse(json)
    fromJson(jsonState)
  }

  def fromJson(json: JsValue): GameState = {
    val result = json.validate[GameState]
    result match {
      case JsSuccess(state, _) => state
      case e: JsError => throw new Exception(JsError.toFlatJson(e).toString())
    }
  }

  def toJson(state: GameState): JsValue = {
    Json.toJson(state)
  }
}
