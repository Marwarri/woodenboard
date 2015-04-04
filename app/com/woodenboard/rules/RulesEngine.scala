package com.woodenboard.rules

import com.woodenboard.dominion.DominionRulesEngine
import com.woodenboard.state.{GameOption, GameState}

/**
 * A RulesEngine is an implementation of the rules of a given game.
 */
abstract class RulesEngine {
  def applyOption(optionId: String, state: GameState): GameState

  def generateOptions(state: GameState): List[GameOption]
}

object RulesEngine {
  def apply(ruleSet: String): RulesEngine = {
    ruleSet match {
      case "dominion" => DominionRulesEngine()
    }
  }
}
