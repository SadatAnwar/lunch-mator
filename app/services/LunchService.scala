package services

import com.google.inject.Inject
import models.{LunchTable, LunchTableRow}
import persistence.repository.LunchTableRows
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class LunchService @Inject()(dbConfigDataProvider: DatabaseConfigProvider) extends Service(dbConfigDataProvider) {

  def createNewTable(lunchTableRow: LunchTableRow) = usingDB {
    LunchTableRows.saveLunchTable(lunchTableRow)
  }

  def getAllLunchTables = usingDB {
    LunchTableRows.getAll
  }

  def getLunchById(id: Int) = usingDB {
    LunchTableRows.getListOfTablesById(id)
  }.flatMap(Function.tupled((a, b, c) => Future(new LunchTable(b, c))))
}
