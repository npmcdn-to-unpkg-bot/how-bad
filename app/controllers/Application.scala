package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

import models.Review
import models.User

object Application extends Controller with Secured {

  def index = withAuth { username => implicit request =>
    Ok(views.html.index(reviewForm, username))
  }

  val reviewForm = Form(tuple("movie" -> nonEmptyText,
                              "comments" -> nonEmptyText))

  def reviews = withAuth { username => implicit request =>
    Ok(views.html.reviews(Review.all, username))
  }

  def newReview = Action { implicit request =>
    reviewForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(errors, null)),
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

  def users = withAuth { username => implicit request =>
    Ok(views.html.users(User.all, username))
  }

  def userProfile(id: Long) = withAuth { username => implicit request =>
    Ok(views.html.profile(User.find(id), username))
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
}
