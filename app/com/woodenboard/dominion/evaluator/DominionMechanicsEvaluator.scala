package com.woodenboard.dominion.evaluator

import com.woodenboard.state._

/**
 * @version $File: $
 *
 */
class DominionMechanicsEvaluator extends OptionEvaluator {

  val evaluatorName = "dominion:evaluator:mechanics"

  /**
   * Applies the option with the given id to the state.
   *
   * @param optionId the id of the option to apply.
   * @param state the state to apply it to.
   */
  override def applyOption(optionId : String, state : GameState): GameState = {
    val option = state.option(optionId).getOrElse {
      throw new Exception(s"option $optionId does not exist in the game state.")
    }
    
    option.optionType match {
      case "dominion:action:all_coins" => playAllCoins(option, state)
      case "dominion:action:end_turn" => endTurn(option, state)
    }
  }

  /**
   * applies the playAllCoins option to the game state.
   *
   * @param option the option to apply.
   * @param state the state to apply it to.
   * @return the resulting game state.
   */
  def playAllCoins(option: GameOption, state : GameState): GameState = {
    val cards = state.findCards(option.optionData("from_deck"), {_.card_class == "Coin"})

    val addedCoinValue = cards.foldLeft(0){_ + _.value}
    val existingCoinValue = state.activeTurn.getOrElse("available_coins", "0").toInt
    val coinValue = existingCoinValue + addedCoinValue

    val manipulator = new StateManipulator() +
      (option.optionData("from_deck"), {_.asInstanceOf[Deck] -- cards}) +
      (option.optionData("to_deck"), {_.asInstanceOf[Deck] ++ cards}) +
      ("active", { _.asInstanceOf[ActiveTurn] + ("available_coins", coinValue.toString) }) +
      ("active", { _.asInstanceOf[ActiveTurn].withoutOptions() })

    state.applyManipulator(manipulator).asInstanceOf[GameState]
  }

  def endTurn(option: GameOption, state: GameState): GameState = {
    state
  }

  def addEndTurnIfApplicable(state: GameState, options: List[GameOption]): List[GameOption] = {
    if (state.activeTurn.phase == "action") {
      val option = GameOption("dominion:action:end_turn", "dominion:action:end_turn", "End Turn", Option.empty)
      options :+ option
    } else {
      options
    }
  }

  def addAllCoinsIfApplicable(state: GameState, options: List[GameOption]): List[GameOption] = {

    val activePlayer = state.activeTurn.player
    val playersHand = state.deckForPlayer(activePlayer, "hand")

    if (state.activeTurn.phase == "action" && playersHand.cards.exists {_.card_class == "Coin"}) {
      val playerActiveDeck = state.deckForPlayer(activePlayer, "active")
      val option = GameOption("dominion:action:all_coins", "dominion:action:all_coins", "All Coins", Option.empty,
        Map("from_deck" -> playersHand.id, "to_deck" -> playerActiveDeck.id))
      options :+ option
    } else {
      options
    }
  }

  def addIndividualCoinActionsIfApplicable(state: GameState, options: List[GameOption]): List[GameOption] = {
    val activePlayer = state.activeTurn.player
    val playersHand = state.deckForPlayer(activePlayer, "hand")
    val playerActiveDeck = state.deckForPlayer(activePlayer, "active")

    if (state.activeTurn.phase == "action") {
      val generatedOptions = playersHand.cards
        .filter {_.card_class == "Coin"}
        .map { card =>
          GameOption(
            "play:" + card.id,
            "dominion:action:coin",
            card.name,
            Option(card.id),
            Map("from_deck" -> playersHand.id, "to_deck" -> playerActiveDeck.id))
        }

      options ++ generatedOptions
    } else {
      options
    }
  }

  /**
   * generates options that are applicable to the current GameState.
   *
   * @param state the state to apply options to.
   * @return the list of applicable options.
   */
  override def generateOptions(state: GameState): List[GameOption] = {
    var options = addEndTurnIfApplicable(state, List())
    options = addAllCoinsIfApplicable(state, options)
    options = addIndividualCoinActionsIfApplicable(state, options)
    options
  }

  /**
   * Lists the option types that can be applied by this evaluator.
   *
   * @return the list.
   */
  override def handledOptions: List[String] = {
    List("dominion:action:coin", "dominion:action:all_coins", "dominion:action:end_turn" )
  }
}