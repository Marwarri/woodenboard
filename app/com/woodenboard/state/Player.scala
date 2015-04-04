package com.woodenboard.state

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import play.api.data.validation.ValidationError

case class Player(
  val id: String,
  val name: String,
  val decks: Map[String, Deck]) extends GameEntity {

  /**
   * Applies the manipulator to the entity and it's child entities.
   *
   * @param manipulator he manipulator to apply.
   * @return a new instance of the entity with the manipulations applied.
   */
  override def applyManipulator(manipulator: StateManipulator): GameEntity = {
    val thisEntity = manipulator.manipulate(this)
    val manipulatedDecks = thisEntity.decks.map {
      case (deckName, deck) => (deckName, deck.applyManipulator(manipulator).asInstanceOf[Deck])
    }
    thisEntity.copy(decks = manipulatedDecks)
  }

  /**
   * Enumerates all of the GameEntity contained inside this entity (including itself) and
   * constructs a map of ids to the instances that they identify.
   *
   * @return the map.
   */
  override def containedIds(): Map[String, GameEntity] = {
    Map(id -> this) ++
    decks.foldLeft(Map[String, GameEntity]()) {_ ++ _._2.containedIds()}
  }
}
