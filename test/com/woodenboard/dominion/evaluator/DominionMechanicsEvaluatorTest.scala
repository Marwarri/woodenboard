package com.woodenboard.dominion.evaluator

import com.woodenboard.dominion.state.StateFixture.BasicState
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

/**
 * Tests the public methods and behaviours of @link DominionMechanicsEvaluator.
 */
@RunWith(classOf[JUnitRunner])
class DominionMechanicsEvaluatorTest extends Specification {
  "The evaluator's applyOption" should {

    "evaluate an all_coins option type by moving all coins to the active cards." in new BasicState {
      val coinCount = state.deck("player:4592994:deck:hand").get.cards.count{_.card_class == "Coin"}

      val resultState = new DominionMechanicsEvaluator().applyOption("dominion:action:all_coins:001", state)

      resultState.deck("player:4592994:deck:hand").get.cards.forall{_.card_class != "Coin"} must beTrue
      resultState.deck("player:4592994:deck:active").get.cards.count{_.card_class == "Coin"} must equalTo(coinCount)
    }

    "evaluate an all_coins option type by adding coin values to the available coins" in new BasicState {
      val coinValue = state.deck("player:4592994:deck:hand").get.cards.foldLeft(0){_ + _.value}

      val resultState = new DominionMechanicsEvaluator().applyOption("dominion:action:all_coins:001", state)

      resultState.activeTurn.turnData("available_coins").toInt must equalTo(coinValue)
    }

    "evaluate an all_coins option type by removing all available options" in new BasicState {
      val resultState = new DominionMechanicsEvaluator().applyOption("dominion:action:all_coins:001", state)

      resultState.activeTurn.options must beEmpty
    }
  }

  "The evaluator's generateApplicableOptions" should {
    "generate an end turn option" in new BasicState {
      val options = new DominionMechanicsEvaluator().generateOptions(state)
      options.find(_.id == "dominion:action:end_turn") must beSome
    }

    "generate an all_coins option if there are available coin cards" in new BasicState {
      val options = new DominionMechanicsEvaluator().generateOptions(state)
      options.find(_.id == "dominion:action:all_coins") must beSome
    }

    "generate an individual coins action for each coin card in the players hand" in new BasicState {
      val options = new DominionMechanicsEvaluator().generateOptions(state)
      val targetCards = options
        .filter(_.optionType == "dominion:action:coin")
        .map(_.target.get)
      val coinCardsInHand = state
        .deckForPlayer(state.activeTurn.player, "hand").cards
        .filter(_.card_class == "Coin")
        .map(_.id)

      targetCards must containAllOf(coinCardsInHand)
      coinCardsInHand must containAllOf(targetCards)
    }
  }
}