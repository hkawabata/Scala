package jp.hkawabata.gatling

import io.gatling.core.Predef._
import io.gatling.core.controller.inject.InjectionStep
import io.gatling.core.feeder.RecordSeqFeederBuilder
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

  val testData1: RecordSeqFeederBuilder[String] = Array(
    Map("keyWord" -> "Solr"),
    Map("keyWord" -> "Apache"),
    Map("keyWord" -> "ネットワーク"),
    Map("keyWord" -> "プログラミング")
  ).random

  val testData2: RecordSeqFeederBuilder[Int] = Array(
    Map("pages" -> 320),
    Map("pages" -> 256),
    Map("pages" -> 192),
    Map("pages" -> 480)
  ).random

  val testData3: RecordSeqFeederBuilder[Any] = Array(
    Map("price" -> 1780, "title" -> "テキスト"),
    Map("price" -> 1980, "title" -> "サーバ"),
    Map("price" -> 1680, "title" -> "ネットワーク"),
    Map("price" -> 3200, "title" -> "入門")
  ).random

  val scn: ScenarioBuilder = scenario("Typical User A")
    .feed(testData1).feed(testData2).feed(testData3)
    .repeat(2){
      exec(http("request_A_1.1")
        .get("/solr/solrbook/select?indent=on&wt=json").queryParam("q", "summary:${keyWord} AND pages:${pages}")
        .check(bodyString.saveAs("key1"))).exitHereIfFailed
        .exec(http("request_A_1.2")
          .get("/solr/solrbook/select?indent=on&wt=json").queryParam("q", "price:${price} AND title:${title}")
          .check(status.is(200))
          .check(bodyString.saveAs("key2"))).exitHereIfFailed
    }.pause(100 milliseconds)
    .exec(http("request_A_2")
    .get("/solr/solrbook/select?indent=on&wt=json").queryParam("q", "*:*")).exitHereIfFailed
    .exec{
      session =>
        val attr: Map[String, Any] = session.attributes
        println(s"${attr.getOrElse("key1", "").asInstanceOf[String].length}, ${attr.getOrElse("key2", "").asInstanceOf[String].length}")
        session
    }

  val scn2: ScenarioBuilder = scenario("Typical User B")
    .repeat(2){
      exec(http("request_B_1.1")
        .get("/solr/solrbook/select?indent=on&wt=json").queryParam("q", "summary:Solr"))
        .exec(http("request_B_1.2")
          .get("/solr/solrbook/select?indent=on&wt=json").queryParam("q", "pages:320"))
    }.pause(100 milliseconds)
    .exec(http("request_B_2")
      .get("/solr/solrbook/select?indent=on&wt=json").queryParam("q", "*:*"))

  /*
  val injections: Seq[InjectionStep with Product with Serializable] = Seq(
    rampUsersPerSec(1).to(50).during(20),
    constantUsersPerSec(50).during(20),
    nothingFor(5 seconds),
    atOnceUsers(200),
    nothingFor(5 seconds),
    rampUsers(1000) over (20 seconds),
    rampUsersPerSec(50).to(1).during(20)
  )
  */
  val injections: Seq[InjectionStep with Product with Serializable] = Seq(
    constantUsersPerSec(5).during(10)
  )

  setUp(scn.inject(injections), scn2.inject(injections)).protocols(httpConf)
}
