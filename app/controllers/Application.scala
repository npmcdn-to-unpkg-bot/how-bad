package controllers

import scala.concurrent.Future
import scala.concurrent.Await
import scala.concurrent.duration._

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.libs.ws._
import play.api.libs.json._
import play.api.Play.current

import models.Review
import models.User

object Application extends Controller with Secured {

  def index = withUser { user => implicit request =>
    Ok(views.html.index(reviewForm, user))
  }

  def makeOmdbRequest(movieName: String): JsValue = {
    val requestHolder: WSRequestHolder =
      WS.url("http://www.omdbapi.com/")
        .withHeaders("Accept" -> "application/json")
        .withRequestTimeout(10000)
        .withQueryString("t" -> movieName)
    Await.result(requestHolder.get, 10 seconds).json
  }

  def makeOmdbRequestById(imdbId: String): JsValue = {
    val requestHolder: WSRequestHolder =
      WS.url("http://www.omdbapi.com/")
        .withHeaders("Accept" -> "application/json")
        .withRequestTimeout(10000)
        .withQueryString("i" -> imdbId)
    Await.result(requestHolder.get, 10 seconds).json
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
        val omdbResponseJson = makeOmdbRequest(formInput._1)
        if ((omdbResponseJson \ "Response").as[String] == "True") {
          Review.create(
            (omdbResponseJson \ "Title").as[String],
            (omdbResponseJson \ "imdbID").as[String],
            formInput._2,
            user.id
          )
          Redirect(routes.Application.index)
        } else {
          Ok("Movie not found!")
        }
      }
    )
  }

  def deleteReview(id: Long) = Action {
    Review.delete(id)
    Redirect(routes.Application.reviews)
  }

  def movie(id: String) = withUser { user => implicit request =>
    val omdbResponseJson = makeOmdbRequestById(id)
    Ok(views.html.movie((omdbResponseJson \ "Title").as[String], (omdbResponseJson \ "Poster").as[String], Review.findByMovieId(id), user))
  }

  def users = withUser { user => implicit request =>
    Ok(views.html.users(User.all, user))
  }

  def userProfile(id: Long) = withUser { user => implicit request =>
    Ok(views.html.profile(User.find(id), Review.findByReviewerId(id), user))
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
