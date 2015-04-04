package com.woodenboard.state

import com.woodenboard.dominion.cards.DominionCard

case class Card(val id: String, val cardType: String) extends GameEntity with DominionCard {

  /**
   * Applies the manipulator to the entity and it's child entities.
   *
   * @param manipulator he manipulator to apply.
   * @return a new instance of the entity with the manipulations applied.
   */
  override def applyManipulator(manipulator: StateManipulator): GameEntity = {
    manipulator.manipulate(this)
  }

  /**
   * Enumerates all of the GameEntity contained inside this entity (including itself) and
   * constructs a map of ids to the instances that they identify.
   *
   * @return the map.
   */
  override def containedIds(): Map[String, GameEntity] = {
    Map(id -> this)
  }
}