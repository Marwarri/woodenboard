package models

import com.woodenboard.dominion.evaluator._
import com.woodenboard.rules.RulesEngine
import com.woodenboard.state.GameState
import play.api.libs.json.JsValue

class GameEngine(var state: GameState, val rules: RulesEngine) {
  def applyOption(optionId : String): GameState = {
    val intermediateState = rules.applyOption(optionId, state)
    val options = rules.generateOptions(intermediateState)
    val resultState = intermediateState + options

    //TODO: save the new state.

    resultState
  }
}

object GameEngine {
  def apply(gameId: String): GameEngine = {
    val state = GameState.load(gameId)
    val rules = RulesEngine(state.ruleSet)

    new GameEngine(state, rules)
  }
}