package jp.hkawabata.network.mail

import java.io._
import java.net.Socket
import java.util.Base64

/**
  * SMTP を使ってメールを送信する
  * cf. RFC5321 (https://tools.ietf.org/html/rfc5321)
  *
  *
  * 実行方法
  *
  * sbt compile "run-main jp.hkawabata.network.mail.Smtp ${serviceName} ${userName} ${password}"
  *
  * - serviceName: メールサービス名 (yahoo or google)
  * - userName: ユーザ名
  * - password: パスワード
  */
object Smtp {
  val port: Map[String, Int] = Map(
    "google" -> 587,
    "yahoo" -> 587
  )
  val smtpServer: Map[String, String] = Map(
    "google" -> "smtp.gmail.com",
    "yahoo" -> "smtp.mail.yahoo.co.jp"
  )
  val addressSuffix: Map[String, String] = Map(
    "google" -> "gmail.com",
    "yahoo" -> "yahoo.co.jp"
  )

  def main(args: Array[String]): Unit = {
    val serviceName = args(0)
    val username = args(1)
    val password = args(2)
    val emailAddress = s"$username@${addressSuffix(serviceName)}"
    val message = List(
      "Hello! こんにちは！",
      "",
      "このメールは Java サンプルプログラムにより送信されています。"
    ).mkString("\r\n")

    val smtp = new Smtp(smtpServer(serviceName), port(serviceName))

    try {
      smtp.send("EHLO localhost")
      smtp.send("AUTH LOGIN")
      smtp.send(Base64.getEncoder.encodeToString(username.getBytes))
      smtp.send(Base64.getEncoder.encodeToString(password.getBytes))
      smtp.send(s"MAIL FROM:$emailAddress")
      smtp.send(s"RCPT TO:$emailAddress")
      smtp.send("DATA")
      smtp.send(
        List(
          s"Subject: hoge",
          s"From: $emailAddress",
          s"To: $emailAddress",
          s"",
          s"$message",
          s"."
        ).mkString("\r\n")
      )
      smtp.send("QUIT")
    } finally {
      smtp.close()
    }
  }
}

class Smtp (server: String, port: Int) {
  val socket = new Socket(server, port)
  val writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream))
  val reader = new BufferedReader(new InputStreamReader(socket.getInputStream))
  readerFlush()

  def send(message: String): Unit = {
    val end = "\r\n"
    writer.write(message + end)
    writer.flush()

    println(s"\n### $message\n")
    readerFlush()
  }

  /**
    *
    */
  def readerFlush(): Unit = {
    var line = ""
    do {
      line = reader.readLine()
      println(line)
    } while (line matches "^[0-9].+-.+")
  }

  def close(): Unit = {
    writer.close()
    reader.close()
    socket.close()
  }
}
