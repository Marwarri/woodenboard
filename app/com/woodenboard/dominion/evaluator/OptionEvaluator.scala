package com.woodenboard.dominion.evaluator

import com.woodenboard.state.{GameOption, GameState}

/**
 * This class encapsulates the rules for a set of player options that can be applied to a game state.
 */
abstract class OptionEvaluator {

  /**
   * Applies the given option to the game state.
   *
   * @param optionId the option to apply.  Must be one of the options returned from the handledOptions method.
   * @param state the state to modify.
   * @return the resulting game state.
   */
  def applyOption(optionId : String, state : GameState): GameState

  /**
   * generates options that are applicable to the current GameState.
   *
   * @param state the state to apply options to.
   * @return the list of applicable options.
   */
  def generateOptions(state: GameState): List[GameOption]

  /**
   * Lists the option types that can be applied by this evaluator.
   *
   * @return the list.
   */
  def handledOptions: List[String]
}