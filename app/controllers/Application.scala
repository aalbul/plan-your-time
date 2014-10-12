package controllers

import com.planyourtime.repository.Repositories
import com.planyourtime.request.{PlanBudgetRequest, EventSearchRequest}
import com.planyourtime.service.EventService
import play.api.libs.json.Json
import play.api.mvc._
import EventSearchRequest._
import com.planyourtime.model.RepositoryInfo._

object Application extends Controller {

  def index = Assets.at("/public/html", "index.htm")
  def search = Assets.at("/public/html", "search.htm")
  def budgetPlanner = Assets.at("/public/html", "budgetPlanner.htm")

  def eventList = Action(parse.json) { implicit request =>
    val req = request.body.as[EventSearchRequest]
    Ok(Json.toJson(EventService.search(req)))
  }

  def repositoryInfo = Action {
    Ok(Json.toJson(Repositories.repositoryInfo))
  }

  def planBudget = Action(parse.json) { implicit request =>
    val req = request.body.as[PlanBudgetRequest]
    Ok(Json.toJson(EventService.eventsForBudget(req.budget, req.recordCount)))
  }
}