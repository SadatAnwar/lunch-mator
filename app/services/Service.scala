package services

trait Service {
  protected def mapFilters(params: Map[String, Seq[String]]): Seq[(String, String)] = {
    val filters: Map[String, Option[Seq[String]]] = params.map(t => t._1 -> Some(t._2.toSeq))
    filters.toSeq.flatMap { case (key, value) =>
      value match {
        case None => Seq()
        case Some(x) if x.isEmpty => Seq((key, ""))
        case Some(x) => x.flatMap(y => Seq((key, y)))
      }
    }
  }
}
