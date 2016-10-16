package services

import com.google.inject.Inject
import mappers.{LunchTableMapper, UserMapper}
import models.{LunchDto, LunchTableRow}
import persistence.repository.LunchTableRows
import play.api.db.slick.DatabaseConfigProvider

class LunchService @Inject()(dbConfigDataProvider: DatabaseConfigProvider) extends Service(dbConfigDataProvider) {

  def createNewTable(lunchTableRow: LunchTableRow) = usingDB {
    LunchTableRows.createLunch(lunchTableRow)
  }

  def getAllLunchTables = usingDB {
    LunchTableRows.getAll
  }

  def getLunchById(id: Int) = usingDB {
    LunchTableRows.getLunchTableById(id)
  }

  def createLunch(lunchDto: LunchDto, userName: String) = usingDB {
    val lunch = LunchTableMapper.map(lunchDto)
    LunchTableRows.createLunch(lunch)
  }
}
