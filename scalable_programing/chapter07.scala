import java.io.FileNotFoundException
import java.io.IOException
import java.io.FileReader

object Main {
  def main(args:Array[String]){
    val filename = if (!args.isEmpty) args(0) else "default.txt"
    
    var line = ""
    do {
      // なんか warning が出る
      line = readLine()
    } while (line != "")

    val filesHere = (new java.io.File(".")).listFiles
    for (file <- filesHere)
      println(file)

    // 1から4まで表示
    for(i <- 1 to 4)
      println(i)
    // 1から3まで表示
    for(i <- 1 until 4)
      println(i)

    // フィルタリング
    for (
      file <- filesHere
      if file.getName.startsWith("Main$")
      if file.getName.endsWith(".class")
    ) println(file)

    // 複数のジェネレータ（** <- ??）をつかうことで入れ子に
    for (
      i <- 1 until 8
      if i % 2 == 0;
      j <- 1 to i
    ) println(j)
    // for 式の中である計算が繰り返される場合、変数に結果を
    // 代入して（束縛して）つかうこともできる。このとき変数に
    // val をつける必要は無い。

    // 1ループごとに破棄されてしまうパラメータを、yield を用いて
    // Array 形式で保存しておける。
    val classList = for (
      file <- filesHere
      if (file.getName.endsWith(".class"))
    ) yield file
    for (cl <- classList)
      println(cl)


    /******** try 文による例外処理 ********/

    // 例外のスロー
    def halfInt(n: Int) = {
      if (n % 2 == 0) n / 2
      else 
        throw new RuntimeException("n must be even")
    }
    // 以下を実行するとエラーが返ってくる
    // println(halfInt(15))
    
    // 例外のキャッチ
    try {
      println(halfInt(14))
      println(halfInt(15))
      // 例外が発生するので以下は実行されない
      println(halfInt(16))
    } catch {
      // 各エラーに対応した処理を行う
      case ex: FileNotFoundException =>
        println("file not found error")
      case ex: RuntimeException =>
        println("runtime error")
      case ex: IOException =>
        println("io error")
    }

    // 式がどんな終わり方をしても実行したい命令があるとき
    val file = new FileReader("imput.txt")
    try{
      //val file2 = new FileReader("aaa.txt")
    } finally {
      println("file closing...")
      file.close()
    }
    
    // try-catch-finally は値を返す
    // java は必ずしもそうでは無い
    
    // try-finally で起こる面倒臭い事例
    def dameFunc1(): Int = try {1} finally {2}
    def dameFunc2(): Int = try {return 1} finally {return 2}
    // コレは1を出力
    println(dameFunc1())
    // コレは2を出力
    println(dameFunc2())
    // finally 節で値を返すのは避けるようにする。
    // 開いているファイルを閉じるなど、絶対に起こしたい副作用にのみ使う。

    
    /******** match 式 ********/
    // switch 文のようなことができる
    // Java と違い、case で整数以外も扱える
    val trafficLite = Array("blue", "yellow", "red")
    for (color <- trafficLite)
      color match {
        case "blue" => println("Go!")
        case "yellow" => println("Stop.")
        case "red" => println("Stoooooop!!!!")
      }

  }
}
