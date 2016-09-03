package services

import play.api.db.slick.DatabaseConfigProvider
import slick.dbio.{DBIOAction, NoStream}
import slick.driver.PostgresDriver.api._

class Service(dbConfigDataProvider: DatabaseConfigProvider) {

  private val db = Database.forConfig("slick.dbs.default.db")

  def usingDB[T](f: => DBIOAction[T, NoStream, Nothing]) = {
    db.run(f)
  }
}
