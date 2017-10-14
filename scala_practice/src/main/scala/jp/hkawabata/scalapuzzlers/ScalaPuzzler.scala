package jp.hkawabata.scalapuzzlers

object ScalaPuzzler {
  def main(args: Array[String]): Unit ={
    run(1, puzzle1, puzzle2, puzzle3, puzzle4, puzzle5)
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

  def puzzle4(): Unit = {
    trait A {
      val large: String
      val small: String = "a"
      println(s"large: $large, small: $small")
    }
    class B extends A {
      val large = "B"
      println(s"large: $large, small: $small")
    }
    class C extends B {
      override val small: String = "c"
      println(s"large: $large, small: $small")
    }
    new C

    trait A2 {
      val large: String
      lazy val small: String = "a2"
      println(s"large: $large, small: $small")
    }
    class B2 extends A2 {
      val large = "B2"
      println(s"large: $large, small: $small")
    }
    class C2 extends B2 {
      override lazy val small: String = "c2"
      println(s"large: $large, small: $small")
    }
    new C2
  }

  def puzzle5(): Unit = {
    def sumSize(itr: Iterable[Iterable[Int]]): Int = {
      itr.map(_.size).sum
    }
    println(sumSize(List(Set(1, 2), Set(3, 4))))
    println(sumSize(Set(List(1, 2), List(3, 4))))
    println(sumSize(List(List(1, 2), Set(3, 4))))
    println(sumSize(Set(List(1, 2), Set(3, 4))))
  }
}
