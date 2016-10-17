package services

import models.LunchRow


class LunchNotFoundException(lunch: LunchRow) extends RuntimeException("Unable to find lunch []" + lunch){
}
