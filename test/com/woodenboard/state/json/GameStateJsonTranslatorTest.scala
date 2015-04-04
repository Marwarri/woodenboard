package com.woodenboard.state.json

import com.woodenboard.dominion.state.StateFixture.BasicState
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.libs.json.Json

import scala.io.Source

/**
 * Tests the public methods and behaviours of @link GameStateJasonTranslator.
 */
@RunWith(classOf[JUnitRunner])
class GameStateJsonTranslatorTest extends Specification {

  "GameState deserialisation for game-state-basic.json" should {
    "contain the game id from the serialised state" in new BasicState {
      state.id must equalTo("game:002383")
    }
    "contain the players in the default game" in new BasicState {
      state.players.map{_.name} must contain("Chris", "Colin")
    }
    "contain a player named Colin with a Copper card in hand" in new BasicState {
      state.players.find { _.name == "Colin" }.get.decks("hand").cards.map {_.name} must contain("Copper")
    }
    "contain an option called End Turn" in new BasicState {
      val name = state.activeTurn.findOptionById("dominion:action:end_turn:001").get.name
      name must equalTo[String]("End Turn")
    }
  }

  "GameState serialisation" should {
    "match the serialised version of the same state." in new BasicState {
      val source = Json.parse(Source.fromFile("test-data/game-state-basic.json", "UTF-8").mkString)
      val translator = new GameStateJsonTranslator {}
      val serialised = translator.toJson(translator.fromJson(source))

      serialised must equalTo(source)
    }
  }
}
