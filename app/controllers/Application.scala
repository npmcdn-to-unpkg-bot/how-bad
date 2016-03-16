package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

import models.Review

object Application extends Controller {

  def index = Action {
    Ok(views.html.index(reviewForm))
  }

  val reviewForm = Form("comments" -> nonEmptyText)

  def reviews = Action {
    Ok(views.html.reviews(Review.all))
  }

  def newReview = Action { implicit request =>
    reviewForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(errors)),
      comments => {
        Review.create(comments)
        Redirect(routes.Application.index)
      }
    )
  }

  def deleteReview(id: Long) = Action {
    Review.delete(id)
    Redirect(routes.Application.reviews)
  }

}
