package controllers

import org.apache.commons.codec.digest.DigestUtils
import play.api.libs.iteratee.Iteratee
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import play.modules.reactivemongo.ReactiveMongoPlugin
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.QueryOpts
import play.api.Play.current

import scala.concurrent.ExecutionContext.Implicits.global

object Application extends Controller {
  val collection = ReactiveMongoPlugin.db.collection[JSONCollection]("cheers")
  collection.convertToCapped(100l, None)

  def index = Action { implicit request =>
    Ok(views.html.index())
  }

  case class Request(email: String, text: String) {
    def asCheer = Cheer(DigestUtils.md5Hex(email), text)
  }
  case class Cheer(md5: String, text: String)

  implicit val requestFormat = Json.format[Request]
  implicit val cheerFormat = Json.format[Cheer]

  def socket = WebSocket.using[JsValue] { request =>
    val in = Iteratee.foreach[JsValue] { msg =>
      val cheer = msg.as[Request].asCheer
      collection.insert(cheer)
    }

    val out = collection.find(Json.obj()).options(QueryOpts().tailable.awaitData).cursor[JsValue].enumerate()

    (in, out)
  }

}
