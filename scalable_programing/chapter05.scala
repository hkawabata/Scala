object Main {
  def main(args:Array[String]){
    // エスケープ文字をそのまま書ける文字列記法
    var str = """ "How are you?"
                  "No bad, thanks." """
    println(str)
    // スペースがジャマなので stripMargin メソッドを使う
    str = """| "How are you?"
             | "No bad, thanks." """.stripMargin
    println(str)
  }
}
