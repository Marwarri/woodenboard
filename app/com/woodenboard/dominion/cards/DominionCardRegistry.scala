package com.woodenboard.dominion.cards

import scala.io.Source
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import play.api.data.validation.ValidationError

object DominionCardRegistry {
  implicit val cardTypeReads : Reads[DominionCardType] = (
    (__ \ "name").read[String] and
    (__ \ "image").read[String] and
    (__ \ "text").read[String] and
    (__ \ "cost").read[Int] and
    (__ \ "value").read[Int] and
    (__ \ "victory_points").read[Int] and
    (__ \ "card_class").read[String] and
    (__ \ "source").read[String]
  )(DominionCardType.apply _)
  
  val cards = loadCards()
  
  def loadCards() = {
    val jsonFile = Source.fromFile("test-data/dominion-card-types.json", "UTF-8")
    val jsonString = Json.parse(jsonFile.mkString)
    parseCards(jsonString)
  }
  
  def parseCards(jsonState : JsValue): Map[String, DominionCardType] = {
    val result = jsonState.validate[Map[String, DominionCardType]]
    result match {
      case JsSuccess(state, _) => state
      case e: JsError => throw new Exception(JsError.toFlatJson(e).toString)
    }
  }
  
  def card(typeName: String) = {
    cards(typeName)
  }
}