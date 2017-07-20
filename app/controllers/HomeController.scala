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
    val ft = MongoClient().getDatabase("platter").getCollection("tweet").find().toFuture()
    val tweets = Await.result(ft, Duration(10, TimeUnit.SECONDS))
    val contentList = tweets.toList.map((x: Document) =>x.getString("content"))
    Ok(views.html.index(contentList))
  }

  def submit() = Action{ implicit request: Request[AnyContent] =>

    val content = request.body.asFormUrlEncoded.get("content").head

    val collection = MongoClient().getDatabase("platter").getCollection("tweet")
    val future = collection.insertOne(Document("content"->content)).toFuture
    Await.result(future, Duration(10, TimeUnit.SECONDS))

    Redirect(routes.HomeController.index()).flashing("success"-> "tweet saved")
  }


}
