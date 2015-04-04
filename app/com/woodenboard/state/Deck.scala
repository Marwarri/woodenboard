package com.woodenboard.state

case class Deck(id: String, cards: List[Card]) extends GameEntity {

  /**
   * Constructs a new deck with the given card at the top of the deck.
   *
   * @param card the card to add.
   * @return the deck.
   */
  def :+(card: Card): Deck = {
    Deck(id, cards :+ card)
  }

  /**
   * Constructs a new deck with the given card on the bottom of the deck.
   *
   * @param card the card to add.
   * @return the deck.
   */
  def +:(card: Card): Deck = {
    Deck(id, card +: cards)
  }

  /**
   * Constructs a new deck with the given card removed.
   *
   * @param card the card to remove.
   * @return the deck.
   */
  def :-(card: Card): Deck = {
    Deck(id, cards.takeWhile(_ != card))
  }

  /**
   * Constructs a new deck with the given cards added.
   *
   * @param cardsToAdd the list of cards to add.
   * @return the new deck.
   */
  def ++(cardsToAdd: List[Card]): Deck = {
    Deck(id, cards ++ cardsToAdd)
  }

  /**
   * Constructs a new deck with the given cards removed.
   *
   * @param cardsToRemove the list of cards to remove.
   * @return the deck.
   */
  def --(cardsToRemove: List[Card]): Deck = {
    Deck(id, cards.filterNot(cardsToRemove.contains(_)))
  }

  /**
   * Applies the manipulator to the entity and it's child entities.
   *
   * @param manipulator he manipulator to apply.
   * @return a new instance of the entity with the manipulations applied.
   */
  override def applyManipulator(manipulator: StateManipulator): GameEntity = {
    val thisEntity = manipulator.manipulate(this)
    val manipulatedCards = thisEntity.cards.map { _.applyManipulator(manipulator).asInstanceOf[Card] }
    thisEntity.copy(cards = manipulatedCards)
  }

  /**
   * Enumerates all of the GameEntity contained inside this entity (including itself) and
   * constructs a map of ids to the instances that they identify.
   *
   * @return the map.
   */
  override def containedIds(): Map[String, GameEntity] = {
    cards.foldLeft(Map[String, GameEntity]()) {_ ++ _.containedIds()} +
      (id -> this)
  }
}