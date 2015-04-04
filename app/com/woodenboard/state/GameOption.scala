package com.woodenboard.state

import play.api.libs.json._

case class GameOption(
    val id: String,
    val optionType: String,
    val name : String,
    val target : Option[String],
    val optionData: Map[String, String] = Map()) extends GameEntity {

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