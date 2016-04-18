package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

import models.Review
import models.User

object Application extends Controller {

  def index = Action {
    Ok(views.html.index(reviewForm))
  }

  val reviewForm = Form(tuple("movie" -> nonEmptyText,
                              "comments" -> nonEmptyText))

  def reviews = Action {
    Ok(views.html.reviews(Review.all))
  }

  def newReview = Action { implicit request =>
    reviewForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(errors)),
      formInput => {
        Review.create(formInput._1, formInput._2)
        Redirect(routes.Application.index)
      }
    )
  }

  def deleteReview(id: Long) = Action {
    Review.delete(id)
    Redirect(routes.Application.reviews)
  }

  def users = Action {
    Ok(views.html.users(User.all))
  }

  def userProfile(id: Long) = Action {
    Ok(views.html.profile(User.find(id)))
  }

  val registrationForm = Form(tuple("username" -> nonEmptyText(1, 15),
                                    "password" -> nonEmptyText(1, 15)))

  def registration = Action {
    Ok(views.html.register(registrationForm))
  }

  def register = Action { implicit request =>
    registrationForm.bindFromRequest.fold(
      errors => BadRequest("Invalid submission"),
      formInput => {
        User.create(formInput._1) // TODO: Password
        Ok("Account created")
      }
    )
  }

  def loginPage = Action {
    Ok(views.html.login(loginForm))
  }

  def login = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      errors => BadRequest("Invalid submission"),
      formInput => {
         //TODO: login
        if (User.authenticate(formInput._1).isDefined) {
          Ok("Logged in")
        } else {
          Ok("凸໒(  ͠ຈ ͟ʖ   ͠ຈ)७凸")
        }
      }
    )
  }

  val loginForm = Form(tuple("username" -> text,
                             "password" -> text))
}
