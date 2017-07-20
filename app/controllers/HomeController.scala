package controllers

import javax.inject._

import org.mongodb.scala._
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class HomeController @Inject()(cc: ControllerComponents)
    extends AbstractController(cc) {

  def index() = Action.async { implicit request: Request[AnyContent] =>
    val ft = MongoClient()
      .getDatabase("platter")
      .getCollection("tweet")
      .find()
      .toFuture()

    ft.map({ s =>
        s.map((x: Document) => x.getString("content"))
      })
      .map(contentList => Ok(views.html.index(contentList.toList)))

  }

  def submit() = Action.async { implicit request: Request[AnyContent] =>
    val content = request.body.asFormUrlEncoded.get("content").head

    val collection = MongoClient().getDatabase("platter").getCollection("tweet")
    val future = collection.insertOne(Document("content" -> content)).toFuture

    future.map(
      s =>
        Redirect(routes.HomeController.index())
          .flashing("success" -> "tweet saved"))
  }

}
