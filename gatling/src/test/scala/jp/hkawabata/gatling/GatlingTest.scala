package jp.hkawabata.gatling

import io.gatling.core.Predef._
import io.gatling.core.controller.inject.InjectionStep
import io.gatling.core.feeder.RecordSeqFeederBuilder
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration._

/**
  * 実行コマンド：sbt gatling:test
  */
class GatlingTest extends Simulation {

  val lb: ListBuffer[String] = new ListBuffer

  before {
    println("\nstress test is about to start!\n")
  }

  after {
    println("\nstress test is finished!\n")
    println(lb.size)
    println(lb.head)
  }

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
  ).circular

  val testData4: RecordSeqFeederBuilder[String] = csv("feed/feeder_test.csv").random

  val scn: ScenarioBuilder = scenario("Typical User A")
    .feed(testData1).feed(testData2).feed(testData3).feed(testData4)
    .exec(http("request_A_1.1")
      .get("/solr/solrbook/select?indent=on&wt=json").queryParam("q", "summary:${keyWord} AND pages:${pages}")
      .check(bodyString.saveAs("key1"))).exitHereIfFailed
    .exec(http("request_A_1.2")
      .get("/solr/solrbook/select?indent=on&wt=json").queryParam("q", "price:${price} AND title:${title}")
      .check(status.is(200))
      .check(bodyString.saveAs("key2"))).exitHereIfFailed
    .exec(http("request_A_1.3")
      .get("/solr/solrbook/select?indent=on&wt=json").queryParam("q", "author:${author}")
      .check(status.is(200))
      .check(bodyString.saveAs("key3"))).exitHereIfFailed
    .exec {
      session =>
        val attr: Map[String, Any] = session.attributes
        val resSize = List(
          attr.getOrElse("key1", "").asInstanceOf[String].length,
          attr.getOrElse("key2", "").asInstanceOf[String].length,
          attr.getOrElse("key3", "").asInstanceOf[String].length
        )
        //println(s"${resSize(0)}\t${resSize(1)}\t${resSize(2)}")
        session
    }
    .doIfOrElse(session => session.attributes.getOrElse("key1", "").asInstanceOf[String].contains("Solr")) {
      exec(http("request_B_3.1").get("/solr/solrbook/select").queryParamMap(
        Map("q" -> "price:1780", "fl" -> "title,pages,price,score")
      ).check(bodyString.saveAs("functionQuery")))
    } {
      exec(http("request_B_3.2").get("/solr/solrbook/select").queryParamSeq(
        Seq(("q", "price:1980"), ("fl", "title,pages,price,score"))
      ).check(bodyString.saveAs("functionQuery")))
    }
    .exec {
      session =>
        //println(session.attributes.getOrElse("functionQuery", "").asInstanceOf[String])
        lb.append(session.attributes.getOrElse("functionQuery", "").asInstanceOf[String])
        session
    }

  val scn2: ScenarioBuilder = scenario("Typical User B")
    .repeat(2) {
      exec(http("request_B_1.1")
        .get("/solr/solrbook/select?indent=on&wt=json").queryParam("q", "summary:Solr"))
        .exec(http("request_B_1.2")
          .get("/solr/solrbook/select?indent=on&wt=json").queryParam("q", "pages:320"))
    }.pause(100 milliseconds)
    .exec(http("request_B_2")
      .get("/solr/solrbook/select?indent=on&wt=json").queryParam("q", "*:*"))

  val injections: Seq[InjectionStep with Product with Serializable] = Seq(
    rampUsersPerSec(1).to(50).during(20).randomized,
    constantUsersPerSec(50).during(20),
    nothingFor(5 seconds),
    atOnceUsers(200),
    nothingFor(5 seconds),
    rampUsers(1000) over (20 seconds),
    rampUsersPerSec(50).to(1).during(20)
  )

  val lightInjections: Seq[InjectionStep with Product with Serializable] = Seq(
    constantUsersPerSec(5).during(10)
  )

  setUp(scn.inject(injections), scn2.inject(injections)).protocols(httpConf).throttle(
    /*
    reachRps(10) in (5 seconds),
    holdFor(5 seconds),
    jumpToRps(10),
    holdFor(10 seconds)
    */
  ).assertions(
    // この条件を満たさなければテスト失敗
    global.responseTime.mean.lessThan(50),
    global.successfulRequests.percent.greaterThan(99)
  )
}
