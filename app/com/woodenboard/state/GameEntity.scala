package com.woodenboard.state

/**
 * Superclass for entities that make up the game state.
 */
abstract class GameEntity {
  /**
   * the unique id for the entity.
   */
  val id: String

  /**
   * Applies the manipulator to the entity and it's child entities.
   *
   * @param manipulator he manipulator to apply.
   * @return a new instance of the entity with the manipulations applied.
   */
  def applyManipulator(manipulator: StateManipulator): GameEntity

  /**
   * Enumerates all of the GameEntity contained inside this entity (including itself) and
   * constructs a map of ids to the instances that they identify.
   *
   * @return the map.
   */
  def containedIds(): Map[String, GameEntity]
}
