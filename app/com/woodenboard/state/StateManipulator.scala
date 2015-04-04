package com.woodenboard.state

/**
 * Created by colinwconnors on 01/04/15.
 */
class StateManipulator(private val transformations: Map[String, List[(GameEntity) => GameEntity]] = Map()) {

  def +(transformation: (String, (GameEntity) => GameEntity)): StateManipulator = {
    transformation match {
      case (transformedId, modifier) =>
        val modifiers = transformations.getOrElse(transformedId, List()) :+ modifier
        new StateManipulator(transformations + (transformedId -> modifiers))
    }
  }

  /**
   * Tests an entity to see if it requires manipulation and returns the resulting entity.
   *
   * @param entity the entity in the current state.
   * @return the entity to be added to the new state.
   */
  def manipulate[T <: GameEntity](entity: T): T = {
    if (transformations.contains(entity.id))
      transformations(entity.id).foldLeft(entity){
        (modifiedEntity: GameEntity, modifier: (GameEntity) => GameEntity) =>
          modifier(modifiedEntity).asInstanceOf[T]
      }
    else
      entity
  }
}
