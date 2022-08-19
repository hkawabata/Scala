package jp.hkawabata.parser_combinator

import scala.util.parsing.combinator._

object MyParser extends JavaTokenParsers {
  def third = s ~ v ~ o ^^ { case subj ~ verb ~ obj => println(subj + verb + obj) }
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
