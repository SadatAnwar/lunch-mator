package services

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider

import com.google.inject.Inject
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import slick.jdbc.JdbcBackend

class Service @Inject()(implicit val dbConfigProvider: DatabaseConfigProvider)
{

  private val dbConfig: DatabaseConfig[JdbcProfile] = dbConfigProvider.get[JdbcProfile]

  private val db: JdbcBackend#DatabaseDef = dbConfig.db

  import dbConfig.driver.api._

  def usingDB[T](f: => DBIOAction[T, NoStream, Nothing]): Future[T] =
  {
    db.run(f)
  }
}

object usingDB
{
  @Inject
  val dbConfigProvider: DatabaseConfigProvider = null

  private val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  private val db = dbConfig.db

  import dbConfig.driver.api._

  def apply[T](f: => DBIOAction[T, NoStream, Nothing]): Future[T] =
  {
    db.run(f)
  }

  def async[T](f: => Future[DBIOAction[T, NoStream, Nothing]]): Future[T] =
  {
    f.flatMap { query =>
      db.run(query)
    }
  }
}
