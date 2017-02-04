package mappers

import models.Page

object PageMapper {

  def map[T](page: Page[T]): Seq[T] = {
    page.items
  }
}
