package com.planyourtime.request

import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
 * Created by nuru
 */
object PlanBudgetRequest {
  implicit val reads = (
      (__ \ "budget").read[Int] and
      (__ \ "recordCount").read[Int])(PlanBudgetRequest.apply _)
}

case class PlanBudgetRequest(budget: Int, recordCount: Int)