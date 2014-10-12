package com.planyourtime.model

import com.planyourtime.util.JodaFormat._
import org.joda.time.LocalDateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
 * Created by nuru
 */
object Event {
  private val dateTimeFormat = "yyyy-MM-dd'T'HH:mm"
  private val f = Format(jodaLocalDateTimeReads(dateTimeFormat), jodaLocalDateTimeWrites(dateTimeFormat))

  def readsBuilder(eventType: String) = {
    (__ \ "title").read[String] and
      (__ \ "city").read[String] and
      (__ \ "start").read[LocalDateTime](f) and
      (__ \ "end").read[LocalDateTime](f) and
      (__ \ "price").read[Int] and
      Reads.pure(eventType)
  }
  
  implicit val writes = {
    ((__ \ "title").write[String] and
      (__ \ "city").write[String] and
      (__ \ "start").write[LocalDateTime](f) and
      (__ \ "end").write[LocalDateTime](f) and
      (__ \ "price").write[Int] and
      (__ \ "eventType").write[String] and
      (__ \ "categories").write[List[String]] and
      (__ \ "participants").write[List[String]])(unlift(Event.unapply))
  }
}

case class Event(title: String,
                 city: String,
                 start: LocalDateTime,
                 end: LocalDateTime,
                 price: Int,
                 eventType: String,
                 categories: List[String],
                 participants: List[String])
