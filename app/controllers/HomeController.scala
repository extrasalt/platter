package controllers

import java.util.concurrent.TimeUnit
import javax.inject._

import org.mongodb.scala._
import play.api.mvc._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index() = Action.async { implicit request: Request[AnyContent] =>
    val ft = MongoClient().getDatabase("platter").getCollection("tweet").find().toFuture()

    ft.map({ s =>
      s.map((x:Document)=>x.getString("content"))
    }).map(contentList => Ok(views.html.index(contentList.toList)) )



  }

  def submit() = Action.async { implicit request: Request[AnyContent] =>

    val content = request.body.asFormUrlEncoded.get("content").head

    val collection = MongoClient().getDatabase("platter").getCollection("tweet")
    val future = collection.insertOne(Document("content" -> content)).toFuture

    future.map(s => Redirect(routes.HomeController.index()).flashing("success" -> "tweet saved"))
  }


}
