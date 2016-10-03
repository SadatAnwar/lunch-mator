package services

import com.google.inject.Inject
import models.LunchTableRow
import persistence.repository.LunchTableRows
import play.api.db.slick.DatabaseConfigProvider

class LunchService @Inject()(dbConfigDataProvider: DatabaseConfigProvider) extends Service(dbConfigDataProvider) {

  def createNewTable(lunchTableRow: LunchTableRow) = usingDB {
    LunchTableRows.saveLunchTable(lunchTableRow)
  }

  def getAllLunchTables = usingDB {
    LunchTableRows.getAll
  }

  def getLunchById(id: Int) = usingDB {
    LunchTableRows.getLunchTableById(id)
  }
}
