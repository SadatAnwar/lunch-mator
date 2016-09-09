package controllers

import com.google.inject.Inject
import play.mvc.Controller

class LunchController @Inject()(/*LunchService*/) extends Controller{

  /**
    * This class should have
    * 1. Create a lunch
    * 2. join a lunch
    * 3. load lunch for today
    *   a. if lunch is anonymous, the user information should not be sent to UI
    *   b. if lunch is not anonymous, the data is sent
    */
}
