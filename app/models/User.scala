package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class User(id: Long, username: String)

object User {

  val user = {
    get[Long]("id") ~
    get[String]("username") map {
      case id~username => User(id, username)
    }
  }

  def all: List[User] = {
    DB.withConnection { implicit conn =>
      SQL("select * from user").as(user *)
    }
  }

  def create(username: String) {
    DB.withConnection { implicit conn =>
      SQL("insert into user (username) values ({username})").on(
        'username -> username
      ).executeUpdate
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit conn =>
      SQL("delete from user where id = {id}").on(
        'id -> id
      ).executeUpdate
    }
  }

}
