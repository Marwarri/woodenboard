package com.woodenboard.state.json

import _root_.com.woodenboard.state._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.Writes._
import play.api.libs.json._

/**
 * Defines the format to use to serialise a game option to and from Json.
 */
object GameOptionFormat {
  implicit val gameOptionReads: Reads[GameOption] = (
      (__ \ "id").read[String] and
      (__ \ "optionType").read[String] and
      (__ \ "name").read[String] and
      (__ \ "target").readNullable[String] and
      (__ \ "optionData").read[Map[String, String]]
    )(GameOption.apply _)

  implicit val gameOptionWrites: Writes[GameOption] = (
      (__ \ "id").write[String] and
      (__ \ "optionType").write[String] and
      (__ \ "name").write[String] and
      (__ \ "target").writeNullable[String] and
      (__ \ "optionData").write[Map[String, String]]
    )(unlift(GameOption.unapply))
}
