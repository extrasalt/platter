package models

import org.mongodb.scala.{Completed, MongoClient, MongoCollection}
import org.mongodb.scala.bson.collection.immutable.Document

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class Tweet(content: String) {

  def document: Document = {
    Document("content" -> content)
  }

  def save: Future[Completed] = {
    Tweet.tweetCollection.insertOne(this.document).toFuture
  }

}

object Tweet {
  val tweetCollection: MongoCollection[Document] =
    MongoClient().getDatabase("platter").getCollection("tweet")

  def all: Future[Seq[String]] = {
    tweetCollection
      .find()
      .toFuture()
      .map((s: Seq[Document]) =>
        s.map((doc: Document) => doc.getString("content")))
  }

}
