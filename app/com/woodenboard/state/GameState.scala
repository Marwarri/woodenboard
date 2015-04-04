package com.woodenboard.state

import _root_.com.woodenboard.state.json.GameStateJsonTranslator
import play.api.libs.Files

import scala.io.Source
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

/**
 * This class represents the state of the game.
 */
case class GameState(id : String,
                     ruleSet: String,
                     players: Set[Player],
                     common: CommonState,
                     activeTurn: ActiveTurn)
  extends GameEntity with InterrogatedGameState {

  /**
   * Applies the manipulator to the entity and it's child entities.
   *
   * @param manipulator he manipulator to apply.
   * @return a new instance of the entity with the manipulations applied.
   */
  override def applyManipulator(manipulator: StateManipulator): GameEntity = {
    val thisEntity = manipulator.manipulate(this)
    val manipulatedPlayers = thisEntity.players.map { _.applyManipulator(manipulator).asInstanceOf[Player] }
    val manipulatedCommonState = thisEntity.common.applyManipulator(manipulator).asInstanceOf[CommonState]
    val manipulatedActiveTurn = thisEntity.activeTurn.applyManipulator(manipulator).asInstanceOf[ActiveTurn]
    thisEntity.copy(players = manipulatedPlayers, common = manipulatedCommonState, activeTurn = manipulatedActiveTurn)
  }

  /**
   * Enumerates all of the GameEntity contained inside this entity (including itself) and
   * constructs a map of ids to the instances that they identify.
   *
   * @return the map.
   */
  override def containedIds(): Map[String, GameEntity] = {
    Map(id -> this) ++
    players.foldLeft(Map[String, GameEntity]()) {_ ++ _.containedIds()} ++
    common.containedIds() ++
    activeTurn.containedIds()
  }

  /**
   * Constructs a new state with the given options added.
   *
   * @param options the options to add.
   * @return the new state.
   */
  def +(options: List[GameOption]): GameState = {
    val turn = activeTurn ++ options
    copy(activeTurn = turn)
  }
}

/**
 * The companion object GameState.  Used to find and load game states.
 */
object GameState extends GameStateJsonTranslator {

  var games = Map[String, GameState]()
  
  def load(gameId : String) = {
    games.getOrElse(gameId, {
      val jsonString = loadState(gameId)
      fromString(jsonString)
    })
  }
  
  def loadState(gameId : String) = {
    val file = Source.fromFile("test-data/game-state-basic.json", "UTF-8")
    file.mkString
  }

  def save(state: GameState): Unit = {
    games = games + (state.id -> state)
  }
}