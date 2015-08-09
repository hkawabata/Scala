class SampleClass{
  var x = 1
  val y = 2

  // 非公開（private）変数
  private var x_pri = 0

  // パラメータ x は var でなく val であることに注意
  // 関数本体の前の等号を省略すると Unit 型扱い
  def add(x: Int) { x_pri += x }
  // 下の書き方でも良いが、上の方が「副作用あり」と
  // 伝わりやすい
  // def add(x: Int): Unit = { x_pri += x }

  def check_x_pri(): Int = x_pri

}

object Main{
  def main(args:Array[String]){
    var scr = new SampleClass
    val scl = new SampleClass
    
    // メンバ変数への代入
    scr.x = 100
    // インスタンスが val でもメンバ変数は書き換えられる
    scl.x = 200
    // 以下の2つはエラー（y は val）
    // Error: scr.y = 300
    // Error: scl.y = 400
    
    println(scr.x)
    println(scr.y)
    println(scl.x)
    println(scl.y)
    
    // scr に別のオブジェクトを代入
    scr = new SampleClass
    // scl は val なのでこんなことはできない
    // Error: scl = new OtherObject

    // scl.x_pri は private なので外部からアクセス不可
    // Error: println(scl.x_pri)

    println(scr.check_x_pri())
    scr.add(1000)
    println(scr.check_x_pri())
    scr.add(1000)
    println(scr.check_x_pri())

    // 複数行にまたがる文でも1文として推論してくれる
    var z = 15 +
            13
    println(z)
    // これはダメ
    // Error: z = 15
    //            + 13
    // これは可
    z = (15
         + 13)
    // 以上の理由から、+ のような中置き演算子は文頭ではなく文末に揃える

  }
}












