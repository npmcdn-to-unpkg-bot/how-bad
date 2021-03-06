package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Review(
  id: Long,
  movie: String,
  movieId: String,
  rating: Int,
  comments: String,
  reviewerId: Long
)

object Review {

  val review = {
    get[Long]("id") ~
    get[String]("movie") ~
    get[String]("movie_id") ~
    get[Int]("rating") ~
    get[String]("comments") ~
    get[Long]("user_id") map {
      case id~movie~movieId~rating~comments~reviewerId =>
        Review(id, movie, movieId, rating, comments, reviewerId)
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

  def findByMovieId(movieId: String): List[Review] = {
    DB.withConnection { implicit conn =>
      SQL("select * from review where movie_id = {movie_id}").on(
        'movie_id -> movieId
      ).as(review *)
    }
  }

  def create(movieName: String, movieId: String, rating: Int, comments: String,
             reviewerId: Long) {
    DB.withConnection { implicit conn =>
      SQL("""insert into review (user_id, movie, movie_id, rating, comments)
             values ({user_id}, {movie}, {movie_id}, {rating}, {comments})""").on(
        'user_id -> reviewerId,
        'movie -> movieName,
        'movie_id -> movieId,
        'rating -> rating,
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
