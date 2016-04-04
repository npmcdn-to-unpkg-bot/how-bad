package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Review(id: Long, movie: String, comments: String)

object Review {

  val review = {
    get[Long]("id") ~
    get[String]("movie") ~
    get[String]("comments") map {
      case id~movie~comments => Review(id, movie, comments)
    }
  }

  def all: List[Review] = {
    DB.withConnection { implicit conn =>
      SQL("select * from review").as(review *)
    }
  }

  def create(movie: String, comments: String) {
    DB.withConnection { implicit conn =>
      SQL("insert into review (movie, comments) values ({movie}, {comments})").on(
        'movie -> movie,
        'comments -> comments
      ).executeUpdate
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit conn =>
      SQL("delete from review where id = {id}").on(
        'id -> id
      ).executeUpdate
    }
  }

}
