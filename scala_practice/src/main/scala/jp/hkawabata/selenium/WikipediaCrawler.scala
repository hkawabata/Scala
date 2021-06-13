package jp.hkawabata.selenium

import org.openqa.selenium.{By, WebDriver, WebElement}
import org.openqa.selenium.chrome.ChromeDriver

import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import scala.collection.JavaConverters.asScalaBufferConverter

object WikipediaCrawler {
  val baseUrl = "https://ja.wikipedia.org/wiki/"
  val sleepTimeMillis = 2000

  def main(args: Array[String]): Unit = {
    // ダウンロードした ChromeDriver のパス（exe/ 配下に配置した場合）
    // https://sites.google.com/a/chromium.org/chromedriver/downloads
    System.setProperty("webdriver.chrome.driver", "./exe/chromedriver")

    val startPage = "クローラ"
    val driver: WebDriver = new ChromeDriver()

    val result = actOnPage(driver, startPage)
    result.foreach(println)
    println(result.size)
    driver.quit()
  }

  def actOnPage(driver: WebDriver, pageTitle: String): Set[String] = {
    driver.get(baseUrl + pageTitle)
    Thread.sleep(sleepTimeMillis)
    val body: WebElement = driver.findElement(By.id("bodyContent"))
    val hrefs: Set[String] = body.findElements(By.tagName("a")).asScala.
      map(_.getAttribute("href")).
      map(URLDecoder.decode(_, StandardCharsets.UTF_8.name)).
      toSet
    val nextPageTitles = hrefs.filter(_.startsWith(baseUrl)).
      map(_.replace(baseUrl, "")).
      filter(url => !url.contains("#") && !url.contains(":"))
    nextPageTitles
  }
}
