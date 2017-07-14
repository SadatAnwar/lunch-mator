package services

import scala.concurrent.Future

import play.api.db.slick.DatabaseConfigProvider

import com.google.inject.Inject
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import slick.jdbc.JdbcBackend

abstract class Service @Inject()(implicit val dbConfigProvider: DatabaseConfigProvider) {

  private val dbConfig: DatabaseConfig[JdbcProfile] = dbConfigProvider.get[JdbcProfile]

  private val db: JdbcBackend#DatabaseDef = dbConfig.db

  import dbConfig.driver.api._

  def usingDB[T](f: => DBIOAction[T, NoStream, Nothing]): Future[T] = {
    db.run(f)
  }
}

