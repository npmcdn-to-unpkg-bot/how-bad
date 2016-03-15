package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

import models.Review

object Application extends Controller {

  def index = Action {
    Redirect(routes.Application.reviews)
  }

  val reviewForm = Form("comments" -> nonEmptyText)

  def reviews = Action {
    Ok(views.html.index(Review.all, reviewForm))
  }

  def newReview = TODO

  def deleteReview(id: Long) = TODO

}
