package com.planyourtime.repository

import com.planyourtime.model.Event

/**
 * Created by nuru
 *
 * Base event repository class
 */
abstract class EventRepository {
  /**
   * List all events in repository
   * @return event list
   */
  def listEvents: List[Event]

  /**
   * List distinct cities from all events in repository
   * @return city list
   */
  def listCities: List[String]

  /**
   * List distinct event types from all events in repository
   * @return event type list
   */
  def listEventTypes: List[String]

  /**
   * List distinct categories from all events in repository
   * @return category list
   */
  def listCategories: List[String]

  /**
   * List distinct participants from all events in repository
   * @return participant list
   */
  def listParticipants: List[String]
}
