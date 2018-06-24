package jp.hkawabata.network.mail

import java.net.Socket
import java.io._


/**
  * 実行方法
  *
  * sbt compile "run ${serviceName} ${userName} ${password}"
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
      pop3.command("user " + username)
      pop3.command("pass " + password)
      pop3.command("list")
      pop3.command("retr 1")
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

  def command(message: String): Unit = {
    val end = "\r\n"
    writer.write(message + end)
    writer.flush()

    println(message)
    readerFlush()
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

  def close(): Unit = {
    writer.close()
    is.close()
    socket.close()
  }
}