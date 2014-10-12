package com.planyourtime.util

import com.planyourtime.model.Event
import play.api.libs.json._

/**
 * Created by nuru
 *
 * Contains different parse instructions for different repository types
 */
object EventReads {
  val concertReads = (Event.readsBuilder("concert") and
    (__ \ "genres").read[List[String]] and
    (__ \ "artists").read[List[String]])(Event.apply _)

  val conferenceReads = (Event.readsBuilder("conference") and
    (__ \ "topics").read[List[String]] and
    (__ \ "speakers").read[List[String]])(Event.apply _)

  val sportEventReads = (Event.readsBuilder("sport") and
    (__ \ "sport").read[String].map(_ :: Nil) and
    (__ \ "teams").read[List[String]])(Event.apply _)
}
