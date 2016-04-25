package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Review(id: Long, movie: String, comments: String, reviewerId: Long)

object Review {

  val review = {
    get[Long]("id") ~
    get[String]("movie") ~
    get[String]("comments") ~
    get[Long]("user_id") map {
      case id~movie~comments~reviewerId => Review(id, movie, comments, reviewerId)
    }
  }

  def all: List[Review] = {
    DB.withConnection { implicit conn =>
      SQL("select * from review").as(review *)
    }
  }

  def findByReviewerId(reviewerId: Long): List[Review] = {
    DB.withConnection { implicit conn =>
      SQL("select * from review where id = {reviewer_id}").on(
        'reviewer_id -> reviewerId
      ).as(review *)
    }
  }

  def create(movie: String, comments: String, reviewerId: Long) {
    DB.withConnection { implicit conn =>
      SQL("""insert into review (user_id, movie, movie_id, rating, comments)
             values ({user_id}, {movie}, {movie_id}, {rating}, {comments})""").on(
        'user_id -> reviewerId,
        'movie -> movie,
        'movie_id -> "tt0000001", // TODO
        'rating -> 0, // TODO
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
