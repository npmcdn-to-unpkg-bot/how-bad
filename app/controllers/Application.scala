package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

import models.Review
import models.User

object Application extends Controller with Secured {

  def index = withUser { user => implicit request =>
    Ok(views.html.index(reviewForm, user))
  }

  val reviewForm = Form(tuple("movie" -> nonEmptyText,
                              "comments" -> nonEmptyText))

  def reviews = withUser { user => implicit request =>
    Ok(views.html.reviews(Review.all, user))
  }

  def newReview = withUser { user => implicit request =>
    reviewForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(errors, user)),
      formInput => {
        Review.create(formInput._1, formInput._2, user.id)
        Redirect(routes.Application.index)
      }
    )
  }

  def deleteReview(id: Long) = Action {
    Review.delete(id)
    Redirect(routes.Application.reviews)
  }

  def users = withUser { user => implicit request =>
    Ok(views.html.users(User.all, user))
  }

  def userProfile(id: Long) = withUser { user => implicit request =>
    Ok(views.html.profile(User.find(id), user))
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
        Redirect(routes.Application.login)
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
          Redirect(routes.Application.index)
            .withSession("username" -> formInput._1)
        } else {
          Redirect(routes.Application.login)
        }
      }
    )
  }

  val loginForm = Form(tuple("username" -> text,
                             "password" -> text))

  def logout = Action { implicit request =>
    Redirect(routes.Application.login).withNewSession.flashing(
      "success" -> "You are now logged out."
    )
  }
}
