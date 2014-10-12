package com.planyourtime.repository

import com.planyourtime.model.Event

/**
 * Created by nuru
 *
 * Composite repository that take events from repository list passed to it.
 */
class CompositeEventRepository(repositories: List[EventRepository]) extends EventRepository {
  override def listEvents: List[Event] = repositories.flatMap(_.listEvents).sortBy(_.title)

  override def listParticipants: List[String] = repositories.flatMap(_.listParticipants).distinct.sorted

  override def listEventTypes: List[String] = repositories.flatMap(_.listEventTypes).distinct.sorted

  override def listCities: List[String] = repositories.flatMap(_.listCities).distinct.sorted

  override def listCategories: List[String] = repositories.flatMap(_.listCategories).distinct.sorted
}
