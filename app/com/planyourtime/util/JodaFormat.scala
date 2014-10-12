package com.planyourtime.util

import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import play.api.data.validation.ValidationError
import play.api.libs.json._
import scala.util.control.{Exception => Exceptions}

/**
 * Created by nuru
 *
 * No support for this in play's default package.
 * Maybe, i need to contribute? ;)
 *
 * https://github.com/playframework/playframework/blob/2.1.x/framework/src/play/src/main/scala/play/api/libs/json/Reads.scala
 */
object JodaFormat {
  def jodaLocalDateTimeReads(pattern: String, corrector: String => String = identity): Reads[LocalDateTime] = new Reads[LocalDateTime] {
    val df = DateTimeFormat.forPattern(pattern)

    def reads(json: JsValue): JsResult[LocalDateTime] = json match {
      case JsNumber(d) => JsSuccess(new LocalDateTime(d.toLong))
      case JsString(s) => parseDate(corrector(s)) match {
        case Some(d) => JsSuccess(d)
        case None => JsError(Seq(JsPath() -> Seq(ValidationError("validate.error.expected.jodadate.format", pattern))))
      }
      case _ => JsError(Seq(JsPath() -> Seq(ValidationError("validate.error.expected.date"))))
    }

    private def parseDate(input: String): Option[LocalDateTime] =
      Exceptions.allCatch[LocalDateTime].opt(LocalDateTime.parse(input, df))
  }

  def jodaLocalDateTimeWrites(pattern: String): Writes[LocalDateTime] = new Writes[LocalDateTime] {
    def writes(d: LocalDateTime): JsValue = JsString(d.toString(pattern))
  }
}
