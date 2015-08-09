/*
 ・コンパニオンオブジェクト・コンパニオンクラスのペアは
 　同じファイルに記述し、名前も同じにする
 ・このペアは互いの private 変数にもアクセスできる
 ・クラスと違い、シングルトンオブジェクトはパラメータを取れない
 ・
*/

// object Sample のコンパニオンクラス
class Sample{
  private var y = 3
}

/*
 シングルトンオブジェクトは、静的なメソッドや
 フィールドの定義場所
*/

/*
 静的（static）とは？
 ----- インスタンスを生成せずにアクセスできるフィールド、
       実行できるメソッドのこと
*/

// シングルトンオブジェクト
// class Sample のコンパニオンオブジェクト
object Sample{
  val x = 5  
  def yvalue(){
    val sample = new Sample
    // private 変数でもアクセス可能
    println(sample.y)
  }
}


// このように、クラスとペアになっていなシングルトンオブジェクトを
// スタンドアロンオブジェクトという

// ここでは Scala アプリケーション Main のエントリーポイントとして使っている
object Main{
  // 実行するには main メソッドが必要
  def main(args:Array[String]){
    // インスタンスを生成せずに変数にアクセス
    println(Sample.x)
    // これは静的フィールドでないので不可
    // println(Sample.y)

    Sample.yvalue()

  }
}

// Application トレイトを使うと main() を定義する必要がない
// ただし、コマンドライン引数が使えないのが難点
object MainApp extends App {
  println(Sample.x)
  Sample.yvalue()
}
