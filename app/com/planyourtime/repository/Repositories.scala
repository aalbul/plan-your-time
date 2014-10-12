package com.planyourtime.repository

import com.planyourtime.model.RepositoryInfo
import com.planyourtime.util.EventReads

/**
 * Created by nuru
 */
object Repositories {
  private val concerts = new JsonResourceEventRepository("Concerts.json")(EventReads.concertReads)
  private val conferences = new JsonResourceEventRepository("Conferences.json")(EventReads.conferenceReads)
  private val sports = new JsonResourceEventRepository("SportEvents.json")(EventReads.sportEventReads)
  val allEvents = new CompositeEventRepository(concerts :: conferences :: sports :: Nil)

  def repositoryInfo = RepositoryInfo(allEvents.listCities, allEvents.listEventTypes, allEvents.listCategories)
}
