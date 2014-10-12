package com.planyourtime.repository

import com.planyourtime.model.Event
import org.joda.time.LocalDateTime
import org.specs2.mutable.Specification

/**
 * Created by nuru
 */
class RepositoriesSuite extends Specification {
  "The repositories" should {
    "list all events in descending title order" in {
      val first = Event("AFC Ajax vs Barcelona", "Rome", new LocalDateTime(2015, 1, 14, 19, 0),
        new LocalDateTime(2015, 1, 14, 20, 30), 40, "sport", "football" :: Nil, "AFC Ajax" :: "Barcelona" :: Nil)
      val last = Event("iOS Developer Day", "Lausanne", new LocalDateTime(2014, 12, 15, 9, 0),
        new LocalDateTime(2014, 12, 15, 18, 0), 200, "conference", "software development" :: "iOS" :: "mobile apps" :: Nil,
        "Steve Jobs" :: Nil)

      val events = Repositories.allEvents.listEvents
      events.size must be equalTo 34
      events.head must be equalTo first
      events.last must be equalTo last
    }

    "list all participants in descending order" in {
      val participants = Repositories.allEvents.listParticipants
      participants.size must be equalTo 36
      participants.head must be equalTo "AFC Ajax"
      participants.last must be equalTo "USA"
    }

    "list all event types in descending order" in {
      val eventTypes = Repositories.allEvents.listEventTypes
      eventTypes.size must be equalTo 3
      eventTypes.head must be equalTo "concert"
      eventTypes.last must be equalTo "sport"
    }

    "list all cities in descending order" in {
      val cities = Repositories.allEvents.listCities
      cities.size must be equalTo 6
      cities.head must be equalTo "Amsterdam"
      cities.last must be equalTo "Rome"
    }

    "list all categories in descending order" in {
      val categories = Repositories.allEvents.listCategories
      categories.size must be equalTo 29
      categories.head must be equalTo "agile"
      categories.last must be equalTo "volleyball"
    }
  }
}
