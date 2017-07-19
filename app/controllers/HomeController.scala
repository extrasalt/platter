package controllers

import java.util.concurrent.TimeUnit
import javax.inject._

import com.mongodb.async.client.Observer
import models.Tweet
import org.mongodb.scala._
import play.api._
import play.api.mvc._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
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
  def index() = Action { implicit request: Request[AnyContent] =>
    val tweets = List("Hello", "It's me", "How are you?", "Hello from the other side")
    Ok(views.html.index(tweets))
  }

  def submit() = Action{ implicit request: Request[AnyContent] =>

    val collection = MongoClient().getDatabase("platter").getCollection("tweet")  
    Await.result(collection.insertOne(Document("content"->"it's me")).toFuture, Duration(10, TimeUnit.SECONDS))

    Redirect(routes.HomeController.index()).flashing("success"-> "tweet saved")
  }


}
