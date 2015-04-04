package controllers

import models.GameEngine
import play.api._

import play.api.mvc._
import com.woodenboard.state.GameState
import play.api.libs.json._
import play.api.libs.functional.syntax._

object StateController extends Controller {
  
  /**
   * Returns the current game state for a given game id.
   */
  def getGameState(gameId : String) = Action {
    val state = GameState.load(gameId)
    
    Ok(GameState.toJson(state))
  }

  implicit val applyOptionReader = (
    (__ \ 'playerId).read[String] and
    (__ \ 'optionId).read[String]
  ) tupled

  /**
   * Applies an available option to the game state.  The request must contain a map containing:
   *
   * {
   *   playerId: [$playerId],
   *   optionId: [$optionId]
   * }
   *
   * $playerId is the id of the player making the move.
   * $optionId is the id of the option being executed.
   *
   * @param gameId the game id for the game being played.
   * @return the new game state.
   */
  def applyOption(gameId: String) = Action(parse.json) { request =>
    request.body.validate[(String, String)].map {
      case (playerId, optionId) => {
        val engine = GameEngine(gameId)
        val state = engine.applyOption(optionId)

        Ok(GameState.toJson(state))
      }
    }.recoverTotal{
      e => BadRequest("Detected Error: " + JsError.toFlatJson(e))
    }
  }
}