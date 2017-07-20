package controllers

import javax.inject._

import org.mongodb.scala._
import play.api.mvc._
import models._
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class HomeController @Inject()(cc: ControllerComponents)
    extends AbstractController(cc) {

  val mongoClient = MongoClient()

  def index() = Action.async { implicit request: Request[AnyContent] =>
    Tweet.all.map(contentList => Ok(views.html.index(contentList.toList)))
  }

  def submit() = Action.async { implicit request: Request[AnyContent] =>
    val content = request.body.asFormUrlEncoded.get("content").head

    Tweet(content).save
      .map(
        s => Redirect(routes.HomeController.index)
      )
  }

}
