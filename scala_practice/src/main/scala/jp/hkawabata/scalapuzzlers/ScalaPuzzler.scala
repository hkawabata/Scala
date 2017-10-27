package jp.hkawabata.scalapuzzlers


object ScalaPuzzler {
  def main(args: Array[String]): Unit ={
    run(1, puzzle01, puzzle02, puzzle03, puzzle04, puzzle05, puzzle06, puzzle07, puzzle08, puzzle09, puzzle10)
    run(11, puzzle11, puzzle12, puzzle13, puzzle14, puzzle15, puzzle16, puzzle17, puzzle18, puzzle19, puzzle20)
  }

  def run(startIndex: Int, functions: (() => Unit)*): Unit = {
    functions.zipWithIndex.foreach{
      case (func, index) =>
        println(s"\n##### Puzzle ${index + startIndex} #####\n")
        func()
    }
  }

  def tryCatch(f: => Unit): Unit = {
    try {
      f
    } catch {
      case e: Throwable => println(e)
    }
  }

  def puzzle01(): Unit = {
    List(1, 2).map { i => println("Hoge"); i * 2 }
    List(1, 2).map {println("Fuga"); _ * 2}
  }

  def puzzle02(): Unit = {
    val Year = 2017; val Month = 10
    // 複数変数への同時代入時、大文字から始まるものは定数としてパターンマッチが走ってしまいコンパイルエラー
    //val (Day, Hour, Minute) = (1, 2, 3)
    val (day, hour, minute) = (1, 2, 3)
    println((Year, Month, day, hour, minute))
  }

  def puzzle03(): Unit = {
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

  def puzzle04(): Unit = {
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

  def puzzle05(): Unit = {
    def sumSize(itr: Iterable[Iterable[Int]]): Int = {
      itr.map(_.size).sum
    }
    println(sumSize(List(Set(1, 2), Set(3, 4))))
    println(sumSize(Set(List(1, 2), List(3, 4))))
    println(sumSize(List(List(1, 2), Set(3, 4))))
    println(sumSize(Set(List(1, 2), Set(3, 4))))
  }

  def puzzle06(): Unit = {
    def funcForList[T](l: List[T], f: (List[T], List[T]) => List[T]): List[T] = f(l, l)
    def funcForListCurried[T](l: List[T])(f: (List[T], List[T]) => List[T]): List[T] = f(l, l)
    def combineStringLists(l1: List[String], l2: List[String]): List[String] = l1 ::: l2
    def combineAnyTypeLists[T](l1: List[T], l2: List[T]): List[T] = l1 ::: l2

    val l: List[String] = List("hoge")
    println(funcForList(l, combineStringLists))
    println(funcForListCurried(l)(combineStringLists))
    // 暗黙の型変換が行えずコンパイルエラー
    // 1つのカッコ内の型パラメータは独立に評価される
    //println(funcForList(l, combineAnyTypeLists))
    // 型を明示すればコンパイルできる
    println(funcForList[String](l, combineAnyTypeLists))
    println(funcForListCurried(l)(combineAnyTypeLists))
  }

  def puzzle07(): Unit = {
    import scala.collection.mutable

    val functions1: mutable.Buffer[() => Int] = mutable.Buffer()
    val functions2: mutable.Buffer[() => Int] = mutable.Buffer()

    val numbers = List(10, 20, 30)
    var j = 0
    for (i <- numbers.indices) {
      functions1.append(() => numbers(i))
      functions2.append(() => numbers(j))
      j += 1
    }

    tryCatch {
      println("functions1")
      functions1.foreach(f => println(f()))
    }
    tryCatch {
      println("functions2")
      functions2.foreach(f => println(f()))
    }
  }

  def puzzle08(): Unit = {
    val xs: Seq[Seq[String]] = Seq(Seq("a", "b", "c"), Seq("d", "e"), Seq("f", "g", "h"), Seq("i", "j", "k"))
    tryCatch {
      val ys: Seq[String] = for (Seq(x, y, z) <- xs) yield x + y + z
      println(ys)
    }
    tryCatch {
      val ys: Seq[String] = xs.map{ case Seq(x, y, z) => x + y + z }
      println(ys)
    }
  }

  def puzzle09(): Unit = {
    tryCatch {
      println(if (math.random > 0.5) XY.X.value else XY.Y.value)
    }
    tryCatch {
      println(if (math.random > 0.5) (new XY2).X.value else (new XY2).Y.value)
    }
    tryCatch {
      println(if (math.random > 0.5) XY3.xValue else XY3.yValue)
    }
  }
  object XY {
    object X {
      val value: Int = Y.value + 1
    }
    object Y {
      val value: Int = X.value + 1
    }
  }
  class XY2 {
    object X {
      val value: Int = Y.value + 1
    }
    object Y {
      val value: Int = X.value + 1
    }
  }
  object XY3 {
    lazy val xValue: Int = yValue + 1
    val yValue: Int = xValue + 1
  }

  def puzzle10(): Unit = {
    import scala.collection.immutable.HashSet

    trait HashCodeOverrided {
      override def hashCode(): Int = {
        println(s"Trait: ${this}")
        super.hashCode()
      }
    }
    // 宣言時点では hashCode は明示的に実装されておらず、super.hashCode は AnyRef.hashCode を呼び出す
    case class HogeTraitMixed(s: String) extends HashCodeOverrided
    case class Hoge(s: String)

    def newHogeDecl = HogeTraitMixed("a")
    // ミックスインする時点で構造透過性に基づく hashCode が実装されているので super.hashCode はそれを呼び出す
    def newHogeInst = new Hoge("a") with HashCodeOverrided
    val setDecl = HashSet(newHogeDecl)
    val setInst = HashSet(newHogeInst)

    println("result:")
    // HashSet.contains は hashCode が一致するかどうかで要素の存在をチェック
    // Iterator.contains は equals で要素の存在をチェック
    println(setDecl.iterator contains newHogeDecl)
    println(setDecl contains newHogeDecl)
    println(setInst.iterator contains newHogeInst)
    println(setInst contains newHogeInst)
  }

  def puzzle11(): Unit = {
    var x = 0
    lazy val y = 1 / x
    try {
      println("in try{}")
      // ここで初めてアクセスされるので y が初期化 => 例外発生
      println(y)
    } catch {
      case _: Exception =>
        println("in catch{}")
        x = 1
        // lazy な変数の初期化時に例外が発生した場合、次のアクセス時にもう一度初期化してくれるので 1 / 1 が評価される
        println(y)
    }
  }

  def puzzle12(): Unit = {
    import scala.collection.SortedSet

    case class RomanNumeral(symbol: String, value: Int)
    implicit object RomanOrdering extends Ordering[RomanNumeral] {
      def compare(x: RomanNumeral, y: RomanNumeral): Int = x.value compare y.value
    }
    val numerals = SortedSet(
      RomanNumeral("A", 1000),
      RomanNumeral("B", 100),
      RomanNumeral("C", 10),
      RomanNumeral("D", 1),
      RomanNumeral("E", 500),
      RomanNumeral("F", 50),
      RomanNumeral("G", 5)
    )

    for (num <- numerals; sym = num.symbol) { print(sym) }
    println("")
    numerals.map(_.symbol).foreach(print)
    println("")
    numerals.toSeq.map(_.symbol).foreach(print)
    println("")
  }

  def puzzle13(): Unit = {
    // コンパイルエラーだが、REPL で実行すると null が代入される
    //val s1: String = s1
    // コンパイルエラーだが、REPL で実行すると文字列 nullnull が代入される
    //val s2: String = s2 + s2
  }

  def puzzle14(): Unit = {}

  def puzzle15(): Unit = {}

  def puzzle16(): Unit = {}

  def puzzle17(): Unit = {}

  def puzzle18(): Unit = {}

  def puzzle19(): Unit = {}

  def puzzle20(): Unit = {}
}
