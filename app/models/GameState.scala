package models

import scala.io.Source
import play.api.libs.json._

/**
 * This class represents the state of the game.
 */
class GameState(val stateDescription : JsValue) {
  
}

/**
 * The companion object GameState.  Used to find and load game states.
 */
object GameState {
  def loadState(gameId : String) = {
    val jsonFile = Source.fromFile("test-data/game-state-basic.json", "UTF-8")
    val stateSource = Json.parse(jsonFile.mkString)
    new GameState(stateSource)
  }
  
  def apply(gameId : String) = {
    loadState(gameId)
  }
}