package com.planyourtime.service

import com.planyourtime.model.Event
import com.planyourtime.repository.Repositories
import com.planyourtime.request.EventSearchRequest

import scala.annotation.tailrec

/**
 * Created by nuru
 *
 * Service to help operate with events
 */
object EventService {

  /**
   * Search for events by specified predicate
   * If all of it's fields are not specified, all events will be returned
   * @param request - request instance
   * @return filtered event list
   */
  def search(request: EventSearchRequest) = {
    Repositories.allEvents.listEvents.filter { event =>
      (request.city.map(_.toLowerCase == event.city.toLowerCase)
        :: request.date.map(_.isEqual(event.start.toLocalDate))
        :: request.eventType.map(_.toLowerCase == event.eventType.toLowerCase)
        :: request.category.map(category => event.categories.exists(_.toLowerCase == category.toLowerCase))
        :: Nil).flatten.forall(_ == true)
    }
  }

  /**
   * Calculate event groups for specified budget
   * @param budget - budget size
   * @param limit - record limit
   * @return event group
   */
  def eventsForBudget(budget: Int, limit: Int) = {
    eventsPowerSetByPrice.reverse.filter { i =>
      i.map { case (price, _) => price }.sum <= budget
    }.flatMap { case (current) => expandPrice(current.toList.map{ case (_, l) => l}) }.take(limit).toList
  }

  /**
   * Group events by price and calculate lazy power set from it
   * @return power set of events by price
   */
  private def eventsPowerSetByPrice = {
    Repositories.allEvents.listEvents.aggregate(Map[Int, List[Event]]())(
      seqop = { case (acc, current) =>
        val status = acc.getOrElse(current.price, Nil)
        acc + (current.price -> (current :: status))
      },
      combop = { case (left, right) =>
        right.foldLeft(left) { case (acc, (key, value)) =>
          val leftValue = acc.getOrElse(key, List())
          acc + (key -> (leftValue ::: value))
        }
      }
    ).toSet.subsets.toSeq
  }

  /**
   * Takes list of lists of combined events and combines it vertically
   * @param combination - list of lists of combined events
   * @return expanded events
   */
  private def expandPrice(combination: List[List[Event]]): List[List[Event]] = {
    val initPath = (1 to combination.size).map(_ => 0).toList

    def shift(depth: Int, path: List[Int]) = {
      val rowProcessed = combination(depth).size - 1 == path(depth)
      val newRowIndex = if (rowProcessed) 0 else path(depth) + 1
      val newPath = path.patch(depth, Seq(newRowIndex), 1)
      val newDepth = if (rowProcessed) depth - 1 else if (depth == combination.size - 1) depth else depth + 1
      newDepth -> newPath
    }

    @tailrec
    def doExpand(depth: Int = combination.size - 1, path: List[Int] = initPath, accumulator: List[List[Event]] = List()): List[List[Event]] = {
      depth match {
        case -1 => accumulator
        case d if d == combination.size - 1 =>
          //Last row. populating accumulator here
          val row = path.zipWithIndex.map { case (horizontal, vertical) => combination(vertical)(horizontal) }
          val (newDeph, newPath) = shift(depth, path)
          doExpand(newDeph, newPath, row :: accumulator)
        case _ =>
          //Somewhere in the middle.
          val (newDeph, newPath) = shift(depth, path)
          doExpand(newDeph, newPath, accumulator)
      }
    }

    doExpand()
  }

}
