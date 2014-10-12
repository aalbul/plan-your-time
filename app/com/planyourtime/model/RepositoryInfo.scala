package com.planyourtime.model

import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
 * Created by nuru
 *
 * Composite model to reduce the amount of requests to server when repository overview needed
 * Like: available cities, event types e.t.c
 */
object RepositoryInfo {
  implicit val writes = (
    (__ \ "cities").write[List[String]] and
    (__ \ "eventTypes").write[List[String]] and
    (__ \ "categories").write[List[String]])(unlift(RepositoryInfo.unapply))
}
case class RepositoryInfo(cities: List[String], eventTypes: List[String], categories: List[String])
