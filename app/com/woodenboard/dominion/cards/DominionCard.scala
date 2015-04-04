package com.woodenboard.dominion.cards

trait DominionCard {
  val cardType : String
  
  def name = {DominionCardRegistry.card(cardType).name}
  def image = {DominionCardRegistry.card(cardType).image}
  def text = {DominionCardRegistry.card(cardType).text}
  def cost = {DominionCardRegistry.card(cardType).cost}
  def value = {DominionCardRegistry.card(cardType).value}
  def victory_points = {DominionCardRegistry.card(cardType).victory_points}
  def card_class = {DominionCardRegistry.card(cardType).card_class}
  def source = {DominionCardRegistry.card(cardType).source}
}