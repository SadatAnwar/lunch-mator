package services

import com.google.inject.Inject
import models.{LunchRow, RestaurantRow}
import persistence.repository.{LunchTableRows, Restaurants}
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Service @Inject()(implicit val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  val db = dbConfig.db

  import dbConfig.driver.api._

  def usingDB[T](f: => DBIOAction[T, NoStream, Nothing]) = {
    db.run(f)
  }

  def usingDBAsync[T](f: => Future[DBIOAction[T, NoStream, Nothing]]) = {
    f.flatMap { query =>
      db.run(query)
    }
  }
}

object usingDB {

  @Inject
  val dbConfigProvider: DatabaseConfigProvider = null

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  val db = dbConfig.db

  import dbConfig.driver.api._

  def apply[T](f: => DBIOAction[T, NoStream, Nothing]) = {
    db.run(f)
  }

  def async[T](f: => Future[DBIOAction[T, NoStream, Nothing]]) = {
    f.flatMap { query =>
      db.run(query)
    }
  }
}
