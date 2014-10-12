package com.planyourtime.repository

import com.planyourtime.model.Event
import play.api.libs.json.{Json, Reads}
import scala.io.Source

/**
 * Created by nuru
 *
 * Json file based repository
 * It require file name and Reads (json parse instructions) to be passed to it to initialize
 * Please note, that after parsing, it hold all events in memory assuming that the size of json is adequate :)
 */
class JsonResourceEventRepository(resourceName: String)(implicit r: Reads[Event]) extends EventRepository {
  override val listEvents = Json
    .parse(Source.fromURI(getClass.getClassLoader.getResource(s"resources/$resourceName").toURI).mkString)
    .as[List[Event]]
    .sortBy(_.title)

  override val listParticipants = listEvents.flatMap(_.participants).distinct.sorted

  override val listEventTypes = listEvents.map(_.eventType).distinct.sorted

  override val listCities = listEvents.map(_.city).distinct.sorted

  override val listCategories = listEvents.flatMap(_.categories).distinct.sorted
}
