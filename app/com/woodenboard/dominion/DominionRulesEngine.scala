package com.woodenboard.dominion

import com.woodenboard.rules.RulesEngine
import com.woodenboard.state.{GameOption, GameState}
import com.woodenboard.dominion.evaluator.{DominionMechanicsEvaluator, OptionEvaluator}

/**
 * Implements the rules for Dominion.
 */
class DominionRulesEngine(val evaluators: Map[String, OptionEvaluator]) extends RulesEngine {

  override def applyOption(optionId: String, state: GameState): GameState = {
    val option = state.option(optionId)
      .getOrElse(throw new IllegalArgumentException(s"OptionId $optionId does not exist in the game state."))

    val optionType = option.optionType
    val evaluator = evaluators.getOrElse(optionType,
      throw new IllegalArgumentException(s"OptionType $optionType does not exist in the evaluator map."))

    evaluator.applyOption(optionId, state)
  }

  override def generateOptions(state: GameState): List[GameOption] = {
    val generatedOptions = evaluators.foldLeft[List[GameOption]](List()){_ ++ _._2.generateOptions(state)}
    generatedOptions
  }
}

object DominionRulesEngine {

  val evaluators: Set[OptionEvaluator] = Set(
      new DominionMechanicsEvaluator()
    )

  def apply(): DominionRulesEngine = {
    val evaluatorMap = evaluators.flatMap {
      evaluator => evaluator.handledOptions.map{
        option => (option, evaluator)
      }
    }.toMap

    new DominionRulesEngine(evaluatorMap)
  }
}
