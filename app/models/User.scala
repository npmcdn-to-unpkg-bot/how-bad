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

  def find(id: Long): User = {
    DB.withConnection { implicit conn =>
      SQL("select * from account where id = {id}").on(
        'id -> id
      ).as(user single)
    }
  }

  def all: List[User] = {
    DB.withConnection { implicit conn =>
      SQL("select * from account").as(user *)
    }
  }

  def create(username: String) {
    DB.withConnection { implicit conn =>
      SQL("insert into account (username) values ({username})").on(
        'username -> username
      ).executeUpdate
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit conn =>
      SQL("delete from account where id = {id}").on(
        'id -> id
      ).executeUpdate
    }
  }

  def authenticate(username: String): Option[User] = {
    DB.withConnection { implicit conn =>
      SQL("select * from account where username = {username}").on(
        'username -> username
      ).as(user singleOpt)
    }
  }

}
