package controllers

import org.apache.commons.codec.digest.DigestUtils
import play.api.libs.iteratee.{Iteratee, Concurrent}
import play.api.libs.json.{Json, JsValue}
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  case class Request(email: String, text: String) {
    def asCheer = Cheer(DigestUtils.md5Hex(email), text)
  }
  case class Cheer(md5: String, text: String)

  implicit val requestFormat = Json.format[Request]
  implicit val cheerFormat = Json.format[Cheer]

  def socket = WebSocket.using[JsValue] { request =>
    val (out, channel) = Concurrent.broadcast[JsValue]

    val in = Iteratee.foreach[JsValue] { msg =>
      val cheer = Json.toJson(msg.as[Request].asCheer)
      channel.push(cheer)
    }

    (in, out)
  }

}
