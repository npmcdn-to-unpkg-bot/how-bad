package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Review(id: Long, comments: String)

object Review {

  val review = {
    get[Long]("id") ~
    get[String]("comments") map {
      case id~comments => Review(id, comments)
    }
  }

  def all: List[Review] = {
    DB.withConnection { implicit conn =>
      SQL("select * from review").as(review *)
    }
  }

  def create(comments: String) {
    DB.withConnection { implicit conn =>
      SQL("insert into review (comments) values ({comments})").on(
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
