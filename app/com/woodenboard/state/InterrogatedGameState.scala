package com.woodenboard.state

/**
 * A trait used to interpret a GameState object.
 */
trait InterrogatedGameState extends GameEntity {

  /**
   * A map of all of the ids in the game to their instances.
   */
  private lazy val entities = containedIds()

  /**
   * Finds the cards in a deck that meet the given condition.
   *
   * @param sourceDeck the id of the deck to find cards in.
   * @param cardCondition the condition that cards must meet.
   * @return the cards that match the condition.
   */
  def findCards(sourceDeck: String, cardCondition: (Card) => Boolean): List[Card] = {
    val source = entities(sourceDeck).asInstanceOf[Deck]
    source.cards.filter(cardCondition)
  }

  /**
   * Finds an option with the given id if it exists in the game state.
   *
   * @param id the id to search for.
   * @return the option if it exists.
   */
  def option(id: String): Option[GameOption] = {
    find[GameOption](id)
  }

  /**
   * Finds a deck with the given id if it exists in the game state.
   *
   * @param id the id to search for.
   * @return the deck.
   */
  def deck(id: String): Option[Deck] = {
    find[Deck](id)
  }

  /**
   * Finds a player with the given id if it exists in the game state.
   *
   * @param id the id to search for.
   * @return the player.
   */
  def player(id: String): Option[Player] = {
    find[Player](id)
  }

  /**
   * Finds a card with the given identifier if it exists in the game state.
   *
   * @param id the id to search for.
   * @return the card.
   */
  def card(id: String): Option[Card] = {
    find[Card](id)
  }

  /**
   * Finds an entity of the given type with the given id or returns Option.empty.
   *
   * @param id the id of the entity to search for.
   * @tparam T the type of the entity to search for.
   * @return the entity.
   */
  def find[T <: GameEntity](id: String): Option[T] = {
    val entity = entities.get(id)
    entity match {
      case instance: Option[T] => instance
      case _ => Option.empty
    }
  }

  /**
   * Finds a deck for a specified player.
   *
   * @param playerId the player id whose deck to fetch.
   * @param deckName the name of the deck to fetch.
   * @return the deck.
   */
  def deckForPlayer(playerId: String, deckName: String): Deck = {
    val foundPlayer = player(playerId).getOrElse {
      throw new Exception(s"Player $playerId does not exist in the game.")
    }

    foundPlayer.decks.getOrElse(deckName, {
      throw new Exception(s"Player $playerId does not have a deck named $deckName")
    })
  }
}
