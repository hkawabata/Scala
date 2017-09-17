package jp.hkawabata.gatling

import io.gatling.core.Predef._
import io.gatling.core.controller.inject.InjectionStep
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
    .repeat(2){
      exec(http("request_1.1")
        .get("/solr/solrbook/select?indent=on&wt=json").queryParam("q", "summary:Solr"))
        .exec(http("request_1.2")
          .get("/solr/solrbook/select?indent=on&wt=json").queryParam("q", "pages:320"))
    }.pause(100 milliseconds)
    .exec(http("request_2")
    .get("/solr/solrbook/select?indent=on&wt=json").queryParam("q", "*:*"))

  val injections: Seq[InjectionStep with Product with Serializable] = Seq(
    rampUsersPerSec(1).to(50).during(20),
    constantUsersPerSec(50).during(20),
    nothingFor(5 seconds),
    atOnceUsers(200),
    nothingFor(5 seconds),
    rampUsers(1000) over (20 seconds),
    rampUsersPerSec(50).to(1).during(20)
  )

  //setUp(scn.inject(atOnceUsers(1)).protocols(httpConf))
  //setUp(scn.inject(rampUsers(100) over (5 seconds)).protocols(httpConf))
  //setUp(scn.inject(rampUsersPerSec(1).to(500).during(120)).protocols(httpConf))
  setUp(scn.inject(injections)).protocols(httpConf)
}
