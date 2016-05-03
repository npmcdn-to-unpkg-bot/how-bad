package controllers

import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

import models.User

object Auth extends Controller {

  val loginForm = Form(tuple("username" -> text,
                             "password" -> text))

  def loginPage = Action {
    Ok(views.html.login(Auth.loginForm))
  }

  def login = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      errors => BadRequest("Invalid submission"),
      formInput => {
        if (User.authenticate(formInput._1).isDefined) {
          Redirect(routes.Application.index)
            .withSession("username" -> formInput._1)
        } else {
          Redirect(routes.Auth.login)
        }
      }
    )
  }

  def logout = Action { implicit request =>
    Redirect(routes.Auth.login).withNewSession.flashing(
      "success" -> "You are now logged out."
    )
  }
}
