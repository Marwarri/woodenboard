package com.woodenboard.dominion.cards

case class DominionCardType(
    val name: String,
    val image: String,
    val text: String,
    val cost: Int,
    val value: Int,
    val victory_points: Int,
    val card_class: String,
    val source: String) {}