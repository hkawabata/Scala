name := "gatling"

version := "0.1"

scalaVersion := "2.11.3"

enablePlugins(GatlingPlugin)

libraryDependencies ++= Seq(
  //"org.scalatest" % "scalatest_2.11" % "3.0.1" % "test",
  "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.2" % "test",
  "io.gatling" % "gatling-test-framework" % "2.2.2" % "test"
)
