package services

import com.google.inject.Inject
import play.Configuration
import play.api.libs.ws.WSClient

class CreateTableService @Inject()(ws: WSClient, configuration: Configuration) extends Service {

  def createNewTableAt(name: String, restaurant: String) = {
    println("table created")
    ws.url("/").get()
  }
}

