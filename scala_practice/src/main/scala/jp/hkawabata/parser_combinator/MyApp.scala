package jp.hkawabata.parser_combinator

import scala.util.parsing.combinator._

object MyApp {
  def main(args: Array[String]): Unit = {
    MyTokenParser("I LOVE MUSIC")
    MyTokenParser("I LIKE MUSIC")
    println(MyRegexParser("Xx1234"))
    println(MyRegexParser("1234Xx"))
  }
}

object MyTokenParser extends JavaTokenParsers {
  def third = s ~ v ~ o ^^ { case subj ~ verb ~ obj => println(s"$subj + $verb + $obj") }
  def s = "I" | "WE"
  def v = "LOVE"
  def o = "YOU" | "MUSIC"

  def apply(s : String) : Unit = {
    parseAll(third, s) match {
      case Success(_, _) => println("Success")
      case x => println("Failure: " + x)
    }
  }
}

object MyRegexParser extends RegexParsers {
  def p1 = """[a-zA-Z]+""".r
  def p2 = """\d+""".r

  def word: Parser[String] = p1 ~ p2 ^^ { case x ~ y =>  x + ":" + y }

  def apply(s: String): String = {
    parseAll(word, s) match {
      case Success(x, y) => x
      case x => "Failure: " + x
    }
  }
}