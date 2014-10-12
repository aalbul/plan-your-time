package com.planyourtime.service

import com.planyourtime.model.Event
import com.planyourtime.request.EventSearchRequest
import org.joda.time.{LocalDate, LocalDateTime}
import org.specs2.mutable.Specification

/**
 * Created by nuru
 */
class EventServiceSuite extends Specification {
  "The service" should {
    "return sorted list of events (no filtering)" in {
      val events = EventService.search(EventSearchRequest(city = None, date = None, eventType = None, category = None))
      events.size must be equalTo 34
      val first = Event("AFC Ajax vs Barcelona", "Rome", new LocalDateTime(2015, 1, 14, 19, 0),
        new LocalDateTime(2015, 1, 14, 20, 30), 40, "sport", "football" :: Nil, "AFC Ajax" :: "Barcelona" :: Nil)
      events.head must be equalTo first
      val last = Event("iOS Developer Day", "Lausanne", new LocalDateTime(2014, 12, 15, 9, 0),
        new LocalDateTime(2014, 12, 15, 18, 0), 200, "conference", "software development" :: "iOS" :: "mobile apps" :: Nil,
        "Steve Jobs" :: Nil)
      events.last must be equalTo last
    }

    "return sorted list of events by city (both case sensitive and insensitive)" in {
      val first = Event("AFC Ajax vs PSV Eindhoven", "Amsterdam", new LocalDateTime(2015, 2, 15, 19, 0),
        new LocalDateTime(2015, 2, 15, 20, 30), 25, "sport", "football" :: Nil, "AFC Ajax" :: "PSV Eindhoven" :: Nil)
      val last = Event("The priority game", "Amsterdam", new LocalDateTime(2015, 6, 13, 15, 0),
        new LocalDateTime(2015, 6, 13, 16, 0), 50, "conference", "software process" :: "agile" :: Nil, "Michael Franken" :: Nil)

      val eventsCaseSensitive = EventService
        .search(EventSearchRequest(city = Some("Amsterdam"), date = None, eventType = None, category = None))
      eventsCaseSensitive.size must be equalTo 11
      eventsCaseSensitive.head must be equalTo first
      eventsCaseSensitive.last must be equalTo last

      val eventsCaseInSensitive = EventService
        .search(EventSearchRequest(city = Some("aMsterDam"), date = None, eventType = None, category = None))
      eventsCaseInSensitive must be equalTo eventsCaseSensitive
    }

    "return sorted list of events by date" in {
      val first = Event("AFC Ajax vs PSV Eindhoven", "Amsterdam", new LocalDateTime(2015, 2, 15, 19, 0),
        new LocalDateTime(2015, 2, 15, 20, 30), 25, "sport", "football" :: Nil, "AFC Ajax" :: "PSV Eindhoven" :: Nil)
      val last = Event("Netherlands vs Brazil", "Amsterdam", new LocalDateTime(2015, 2, 15, 9, 0),
        new LocalDateTime(2015, 2, 15, 11, 30), 35, "sport", "volleyball" :: Nil, "Netherlands" :: "Brazil" :: Nil)

      val eventsByDate = EventService
        .search(EventSearchRequest(city = None, date = Some(new LocalDate(2015, 2, 15)), eventType = None, category = None))

      eventsByDate.size must be equalTo 4
      eventsByDate.head must be equalTo first
      eventsByDate.last must be equalTo last
    }

    "return sorted list of events by event type (both case sensitive and insensitive)" in {
      val first = Event("AFC Ajax vs Barcelona", "Rome", new LocalDateTime(2015, 1, 14, 19, 0),
        new LocalDateTime(2015, 1, 14, 20, 30), 40, "sport", "football" :: Nil, "AFC Ajax" :: "Barcelona" :: Nil)
      val last = Event("Netherlands vs Brazil", "Amsterdam", new LocalDateTime(2015, 2, 15, 9, 0),
        new LocalDateTime(2015, 2, 15, 11, 30), 35, "sport", "volleyball" :: Nil, "Netherlands" :: "Brazil" :: Nil)

      val eventsByTypeCaseSensitive = EventService
        .search(EventSearchRequest(city = None, date = None, eventType = Some("sport"), category = None))

      eventsByTypeCaseSensitive.size must be equalTo 10
      eventsByTypeCaseSensitive.head must be equalTo first
      eventsByTypeCaseSensitive.last must be equalTo last

      val eventsByTypeCaseInSensitive= EventService
        .search(EventSearchRequest(city = None, date = None, eventType = Some("SporT"), category = None))
      eventsByTypeCaseInSensitive must be equalTo eventsByTypeCaseSensitive
    }

    "return sorted list of events by category (both case sensitive and insensitive)" in {
      val first = Event("Alter Bridge Live", "Amsterdam", new LocalDateTime(2015, 1, 9, 16, 0),
        new LocalDateTime(2015, 1, 9, 19, 0), 60, "concert", "hard rock" :: "post grunge" :: "heavy metal" :: Nil,
        "Alter Bridge" :: Nil)
      val last = Event("Queens of the Stone Age Live", "Rome", new LocalDateTime(2014, 11, 9, 20, 0),
        new LocalDateTime(2014, 11, 9, 22, 0), 35, "concert", "hard rock" :: Nil, "Queens of the Stone Age" :: Nil)

      val eventsByCategoryCaseSensitive = EventService
        .search(EventSearchRequest(city = None, date = None, eventType = None, category = Some("hard rock")))

      eventsByCategoryCaseSensitive.size must be equalTo 3
      eventsByCategoryCaseSensitive.head must be equalTo first
      eventsByCategoryCaseSensitive.last must be equalTo last

      val eventsByCategoryCaseInSensitive = EventService
        .search(EventSearchRequest(city = None, date = None, eventType = None, category = Some("harD rOck")))
      eventsByCategoryCaseInSensitive must be equalTo eventsByCategoryCaseSensitive
    }

    "return sorted list of events using more then one filter" in {
      val first = Event("AFC Ajax vs PSV Eindhoven", "Amsterdam", new LocalDateTime(2015, 2, 15, 19, 0),
        new LocalDateTime(2015, 2, 15, 20, 30), 25, "sport", "football" :: Nil, "AFC Ajax" :: "PSV Eindhoven" :: Nil)
      val last = Event("Feyenoord vs Celtic FC", "Amsterdam", new LocalDateTime(2015, 2, 15, 17, 30),
        new LocalDateTime(2015, 2, 15, 19, 30), 30, "sport", "football" :: Nil, "Feyenoord" :: "Celtic FC" :: Nil)

      val events = EventService.search(EventSearchRequest(city = Some("Amsterdam"), date = Some(new LocalDate(2015, 2, 15)),
        eventType = Some("sport"), category = Some("football")))
      events.size must be equalTo 2
      events.head must be equalTo first
      events.last must be equalTo last
    }

    "return events for specified budget" in {
      val events = EventService.eventsForBudget(budget = 60, limit = 10)
      events.size must be equalTo 10
      events.forall(_.size == 2) must beTrue

      events.head.head must be equalTo Event("AFC Ajax vs PSV Eindhoven", "Amsterdam", new LocalDateTime(2015, 2, 15, 19, 0),
        new LocalDateTime(2015, 2, 15, 20, 30), 25, "sport", "football" :: Nil, "AFC Ajax" :: "PSV Eindhoven" :: Nil)
      events.head.last must be equalTo Event("Feyenoord vs Celtic FC", "Amsterdam", new LocalDateTime(2015, 2, 15, 17, 30),
        new LocalDateTime(2015, 2, 15, 19, 30), 30, "sport", "football" :: Nil, "Feyenoord" :: "Celtic FC" :: Nil)

      events.last.head must be equalTo Event("Ska-P Live", "Berlin", new LocalDateTime(2015, 6, 12, 20, 0),
        new LocalDateTime(2015, 6, 12, 22, 0), 25, "concert", "ska" :: Nil, "Ska-P" :: Nil)
      events.last.last must be equalTo Event("UML Concepts", "Dusseldorf", new LocalDateTime(2014, 11, 10, 9, 0),
        new LocalDateTime(2014, 11, 10, 11, 0), 30, "conference", "software process" :: "uml" :: Nil, "Grady Booch" :: Nil)
    }

    "return no events when budget is very small" in {
      val events = EventService.eventsForBudget(budget = 1, limit = 10)
      events.size must be equalTo 0
    }
  }
}
