package jp.hkawabata.selenium

import org.openqa.selenium.{By, WebDriver, WebElement}
import org.openqa.selenium.chrome.ChromeDriver

/**
 * usage:
 *   sbt compile "runMain jp.hkawabata.selenium.SeleniumMain
 */

object ChromeExampleMain {
  def main(args: Array[String]): Unit = {
    // ダウンロードした ChromeDriver のパス（exe/ 配下に配置した場合）
    // https://sites.google.com/a/chromium.org/chromedriver/downloads
    System.setProperty("webdriver.chrome.driver", "./exe/chromedriver")

    val driver: WebDriver = new ChromeDriver()
    driver.get("http://www.google.com/xhtml")
    Thread.sleep(5000)
    val searchBox: WebElement = driver.findElement(By.name("q"))
    searchBox.sendKeys("ChromeDriver")
    searchBox.submit()
    Thread.sleep(5000)
    driver.quit()
  }
}
