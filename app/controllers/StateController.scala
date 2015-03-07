package controllers

import play.api._

import play.api.mvc._
import models.GameState

object StateController extends Controller {
  
  /**
   * Returns the current game state for a given game id.
   */
  def getGameState(gameId : String) = Action {
    val state = GameState(gameId)
    
    Ok(state.stateDescription)
  }
}