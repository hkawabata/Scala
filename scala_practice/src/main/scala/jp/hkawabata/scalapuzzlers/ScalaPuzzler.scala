package jp.hkawabata.scalapuzzlers

object ScalaPuzzler {
  def main(args: Array[String]): Unit ={
    run(1, puzzle1)
    run(2, puzzle2)
  }


  def run(num: Int, func: () => Unit): Unit = {
    println(s"\n##### $num #####\n")
    func()
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
}
