package com.woodenboard.dominion.state

import com.woodenboard.state.GameState
import org.specs2.specification.Scope

/**
 * This class aids tests by constructing game state fixtures.
 */
object StateFixture {
  trait BasicState extends Scope {
    val state = GameState.load("1")
  }
}
