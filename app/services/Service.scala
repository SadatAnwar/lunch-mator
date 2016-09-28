package services

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

class Service @Inject()(dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db

  import dbConfig.driver.api._

  def usingDB[T](f: => DBIOAction[T, NoStream, Nothing]) = {
    db.run(f)
  }
}
