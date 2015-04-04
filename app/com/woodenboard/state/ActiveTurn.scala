package com.woodenboard.state

case class ActiveTurn (
    player: String,
    phase: String,
    options: Set[GameOption],
    turnData: Map[String, String]) extends GameEntity {

  /**
   * The id for the turn.
   */
  val id = "active"

  /**
   * Applies the manipulator to the entity and it's child entities.
   *
   * @param manipulator he manipulator to apply.
   * @return a new instance of the entity with the manipulations applied.
   */
  override def applyManipulator(manipulator: StateManipulator): GameEntity = {
    val thisEntity = manipulator.manipulate(this)
    val manipulatedOptions = thisEntity.options.map {_.applyManipulator(manipulator).asInstanceOf[GameOption]}
    thisEntity.copy(options = manipulatedOptions)
  }

  /**
   * Gets the value of the given key in the turn's metadata.
   *
   * @param key the key in the data to fetch.
   * @param default the action to execute if the data is not present.
   * @tparam B the type of the return.
   * @return the value or the result of the default.
   */
  def getOrElse[B >: String](key: String, default: => B): B = {
    turnData.getOrElse(key, default)
  }

  /**
   * Constructs a new turn with the given key added to the turn Data.
   *
   * @param turnDatum the data to add.
   * @return the new turn.
   */
  def +(turnDatum: (String, String)): ActiveTurn = {
    copy(turnData = turnData + turnDatum)
  }

  /**
   * Returns a copy of the turn without any options.
   *
   * @return the new turn.
   */
  def withoutOptions(): ActiveTurn = {
    copy(options = Set())
  }

  /**
   * Constructs a copy of the turn with the given option added to the set of available options.
   *
   * @param option the option to add.
   * @return the new turn.
   */
  def +(option: GameOption): ActiveTurn = {
    copy(options = options + option)
  }

  /**
   * Constructs a copy of the turn with the given options add to the set of available options.
   *
   * @param optionsToAdd the options to add.
   * @return the new turn.
   */
  def ++(optionsToAdd: List[GameOption]): ActiveTurn = {
    copy(options = options ++ optionsToAdd)
  }

  /**
   * Finds an option with the specified id if it exists.
   *
   * @param optionId the unique id of the option.
   * @return the option.
   */
  def findOptionById(optionId: String): Option[GameOption] = {
    options.find(_.id == optionId)
  }

  /**
   * Enumerates all of the GameEntity contained inside this entity (including itself) and
   * constructs a map of ids to the instances that they identify.
   *
   * @return the map.
   */
  override def containedIds(): Map[String, GameEntity] = {
    options.foldLeft(Map[String, GameEntity]()) {_ ++ _.containedIds()} +
      (id -> this)
  }
}