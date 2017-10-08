package jp.hkawabata.scalapuzzlers

object ScalaPuzzler {
  def main(args: Array[String]): Unit ={
    run(1, puzzle1, puzzle2, puzzle3)
  }


  def run(startIndex: Int, functions: (() => Unit)*): Unit = {
    functions.zipWithIndex.foreach{
      case (func, index) =>
        println(s"\n##### Puzzle ${index + startIndex} #####\n")
        func()
    }
  }

  def puzzle1(): Unit = {
    List(1, 2).map { i => println("Hoge"); i * 2 }
    List(1, 2).map {println("Fuga"); _ * 2}
  }

  def puzzle2(): Unit = {
    val Year = 2017; val Month = 10
    // 複数変数への同時代入時、大文字から始まるものは定数としてパターンマッチが走ってしまいコンパイルエラー
    //val (Day, Hour, Minute) = (1, 2, 3)
    val (day, hour, minute) = (1, 2, 3)
    println((Year, Month, day, hour, minute))
  }

  def puzzle3(): Unit = {
    trait HogeTrait {
      val audience: String
      println(s"Trait: $audience")
      def hello(): Unit = println(s"Hello, $audience.")
    }
    class Hoge1 extends HogeTrait {
      val audience = "Hoge1"
      println(s"ImplementedClass: $audience")
    }
    class Hoge2(val audience: String = "Hoge2_default") extends HogeTrait {
      println(s"ImplementedClass: $audience")
    }
    class Hoge3(a: String) extends HogeTrait {
      override val audience: String = a
      println(s"ImplementedClass: $audience")
    }
    new Hoge1().hello()
    new Hoge2("Hoge2").hello()
    new Hoge3("Hoge3").hello()
  }
}
