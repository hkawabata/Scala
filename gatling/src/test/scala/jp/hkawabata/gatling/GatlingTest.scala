package jp.hkawabata.gatling

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import scala.concurrent.duration._

class GatlingTest extends Simulation {
  val httpConf: HttpProtocolBuilder = http
    .baseURL("http://node001.hkawabata.jp:8983")
    /*
    .headers(Map(
      HttpHeaderNames.ContentType    -> HttpHeaderValues.ApplicationJson,
      HttpHeaderNames.Accept         -> HttpHeaderValues.ApplicationJson,
      HttpHeaderNames.AcceptEncoding -> "gzip,deflate"
    ))*/

  val scn: ScenarioBuilder = scenario("Basic sinario")
    .exec(http("request_1")
      .get("/solr"))
    .pause(500 milliseconds)
    .exec(http("request_2")
      .get("/solr"))

  //setUp(scn.inject(atOnceUsers(1)).protocols(httpConf))
  setUp(scn.inject(rampUsers(100) over (5 seconds)).protocols(httpConf))
}
