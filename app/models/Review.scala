package models

case class Review(id: Long, comments: String)

object Review {

  def all: List[Review] = Nil

  def create(comments: String) {}

  def delete(id: Long) {}

}
