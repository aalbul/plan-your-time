package com.planyourtime.request

import org.joda.time.LocalDate
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
 * Created by nuru
 */
object EventSearchRequest {
  private implicit val dateReads = Reads.jodaLocalDateReads("yyyy-MM-dd")
  implicit val reads = (
    (__ \ "city").readNullable[String] and
    (__ \ "date").readNullable[LocalDate] and
    (__ \ "eventType").readNullable[String] and
    (__ \ "category").readNullable[String])(EventSearchRequest.apply _)
}

case class EventSearchRequest(city: Option[String], date: Option[LocalDate], eventType: Option[String], category: Option[String])
