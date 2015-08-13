// 分数クラス
class Rational(n: Int, d: Int = 1){
  // ここで指定した条件を満たしていないと
  // エラーを返してインスタンスを生成しない
  require(d != 0)

  private val g = gcd(n.abs, d.abs)

  val numer = n / g
  val denom = d / g

  // toString を override
  // これをしないと println やインタプリタの "res0:" は <クラス名>@<16進数> を返す
  override def toString = numer + " / " + denom


  // 四則演算
  def + (r: Rational): Rational = 
    new Rational(r.denom * numer + r.numer * denom, r.denom * denom)
  def - (r: Rational): Rational = 
    new Rational(r.denom * numer - r.numer * denom, r.denom * denom)
  def * (r: Rational): Rational = 
    new Rational(r.numer * numer, r.denom * denom)
  def / (r: Rational): Rational = 
    new Rational(r.denom * numer, r.numer * denom)
  // メソッドの多重定義（分数に整数を足せるように）
  // ただし、これだと分数+整数はできても整数+分数はできない
  // そのため、Main クラス内で暗黙の型変換が必要
  def + (i: Int): Rational =
    new Rational(i * denom + numer, denom)

  // 大小関係
  def < (r: Rational): Boolean =
    if(numer * r.denom < denom * r.numer) true else false
  def > (r: Rational): Boolean =
    if(numer * r.denom > denom * r.numer) true else false
  def <= (r: Rational): Boolean =
    if(numer * r.denom <= denom * r.numer) true else false
  def >= (r: Rational): Boolean =
    if(numer * r.denom >= denom * r.numer) true else false

  def max(r: Rational): Rational =
    if(this > r) this else r

  // 約分のため、最小公倍数を求める private メソッドを追加
  private def gcd(a: Int, b: Int): Int = 
    if(b == 0) a else gcd(b, a % b)

}

object Main {
  def main(args:Array[String]){
    val rat = new Rational(12, 5)
    val rat2 = new Rational(3)
    println(rat + " + " + rat2 + " = " + (rat + rat2))
    println(rat + " - " + rat2 + " = " + (rat - rat2))
    println(rat + " * " + rat2 + " = " + (rat * rat2))
    println(rat + " / " + rat2 + " = " + (rat / rat2))
    println(rat + " <= " + rat2 + " ? : " + (rat <= rat2))
    println(rat + " > " + rat2 + " ? : " + (rat > rat2))

    println("Which is larger? : " + rat.max(rat2))
    
    println(rat + 1)

    // 必要に応じて自動的に整数を分数に変換する「暗黙の型変換」
    // なぜか以下の warning が出る
    // warning: there was one feature warning; re-run with -feature for details
    implicit def intToRational(i: Int) = new Rational(i)
    println(1 + rat)
    println(rat - 1)
    println(5 * rat)
  }
}
