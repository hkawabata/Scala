package jp.hkawabata.network.mail

import java.net.Socket
import java.io._


/**
  * POP3 によりメールサーバからメールを取得する
  * cf. RFC1934 https://www.ietf.org/rfc/rfc1939.txt
  *
  *
  * 実行方法
  *
  * sbt compile "run-main jp.hkawabata.network.mail.Pop3 ${serviceName} ${userName} ${password}"
  *
  * - serviceName: メールサービス名 (yahoo or google)
  * - userName: ユーザ名
  * - password: パスワード
  */
object Pop3 {
  val port: Map[String, Int] = Map(
    "google" -> 110,
    "yahoo" -> 110
  )
  val popServer: Map[String, String] = Map(
    "google" -> "pop.gmail.com",
    "yahoo" -> "pop.mail.yahoo.co.jp"
  )

  def main(args: Array[String]): Unit = {
    val serviceName = args(0)
    val username = args(1)
    val password = args(2)

    val pop3 = new Pop3(popServer(serviceName), port(serviceName))

    try {
      pop3.send("user " + username)
      pop3.send("pass " + password)
      pop3.send("list")
      pop3.send("retr 1")
    } finally {
      pop3.close()
    }
  }
}

class Pop3 (server: String, port: Int) {
  val socket = new Socket(server, port)
  val writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream))
  val is = socket.getInputStream
  readerFlush()

  def send(message: String): Unit = {
    val end = "\r\n"
    writer.write(message + end)
    writer.flush()

    println(message)

    if (message.startsWith("user") || message.startsWith("pass")) {
      readerFlush()
    } else {
      readerFlushMultipleLines()
    }
  }

  def readerFlush(): Unit = {
    val sb = new StringBuilder
    var char = is.read()
    var before = ' '.toInt
    while (before != '\r' && char != '\n') {
      sb.append(char.toChar)
      before = char
      char = is.read()
    }
    println(sb.result())
    println("-------------------")
  }

  def readerFlushMultipleLines(): Unit = {
    val sb = new StringBuilder
    var char = ' '
    var b1 = ' '
    var b2 = ' '
    var b3 = ' '
    var b4 = ' '
    while (List(b4, b3, b2, b1, char).mkString != "\r\n.\r\n") {
      b4 = b3
      b3 = b2
      b2 = b1
      b1 = char
      char = is.read().toChar
      sb.append(char)
    }
    println(sb.result())
    println("-------------------")
  }

  def close(): Unit = {
    writer.close()
    is.close()
    socket.close()
  }
}