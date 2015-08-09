class SampleClass{
  var x = 1
  val y = 2
}

object Main{
  def main(args:Array[String]){
    var scr = new SampleClass
    val scl = new SampleClass
    
    // メンバ変数への代入
    scr.x = 5
    // インスタンスが val でもメンバ変数は書き換えられる
    scl.x = 10
    // 以下の2つはエラー（y は val）
    // scr.y = 15
    // scl.y = 20
    
    println(scr.x)
    println(scr.y)
    println(scl.x)
    println(scl.y)
    
    // scr に別のオブジェクトを代入
    scr = new SampleClass
    // scl は val なので、このようなことは不可
    // scl = new OtherObject

    
    
  }
}












