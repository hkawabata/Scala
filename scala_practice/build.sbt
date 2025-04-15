name := "scala_practice"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.5" % "test",
  // Selenium
  // https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-chrome-driver
  "org.seleniumhq.selenium" % "selenium-chrome-driver" % "3.141.59",
  // https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java
  "org.seleniumhq.selenium" % "selenium-java" % "3.141.59",
  // https://mvnrepository.com/artifact/org.scala-lang.modules/scala-parser-combinators
  "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1"
  // ...
)