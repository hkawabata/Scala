// Chapter 03


// new を使いクラスのインスタンスを生成
val big = new java.math.BigInteger("12345")
// インスタンスのパラメータとして型を指定
val greetStrings = new Array[String](3)
greetStrings(0) = "Hello"
greetStrings(1) = ", "
greetStrings(2) = "world!\n"
for (i <- 0 to 2) print(greetStrings(i))
// よりよい書き方
val greetStrings2: Array[String] = new Array[String](3) 

/*
【重要】
ここで、greetStrings は val で宣言しているが、値の書き換えが
できている。
-> val で宣言した変数に再代入することはできないが、変数が
   参照するオブジェクトは変わる可能性がある。
ここでは、greetStrings が参照する先（new で生成した Array[String]
インスタンス）を別のインスタンスに変えることはできないが、
参照先の値を変更することができる。
よって、配列自体はミュータブル。
*/

/*
【重要】
Scala においては、すべての演算がメソッド呼び出しである。

例1）上の例で、for 文中の "0 to 2" は (0).to(2) のこと。0というオブジェクトが
持つ to() メソッドを呼び出している。このように、
	オブジェクト.メソッド(引数)
という構文は
	オブジェクト メソッド 引数
と書き換えられる。

例2）println は Console オブジェクトのメソッドであり、
	println 10
はダメだが
	Console println 10
ならOK。

例3）+, -, *, / などもメソッド。
	1 + 2
は
	(1).+(2)
とも書ける。
*/
Console.println((10).+(8))



// 配列の値の変更、呼び出しは共にメソッドであり、
// 以下の2ブロックは全く同一
greetStrings(0) = "Hello"
println(greetStirngs(0))

greetStrings.update(0, "Hello")
println(greetStirngs.apply(0))


// 簡潔な配列の作成・初期化（こちらを頻繁に使う）
val numNames = Array("zero", "one", "two")  // 型推論してくれている

/*
【重要】
関数型プログラミングにおいては、メソッドは副作用を持ってはならない。
*/

/********** List **********/


// Array の参照先の値はミュータブルだが、List (scala.List)の値はイミュータブル。
// java の List (java.util.List)はミュータブルにできる。

// 空の List。List() でも良い
val list_empty = Nil

// List の初期化
val oneTwoThree = List(1, 2, 3)
val fourFive = 4 :: 5 :: Nil

// List の結合
val oneTwoThreeFourFive = oneTwoThree ::: fourFive
println(oneTwoThreeFourFive)

// List への要素の追加
val cde = List('c', 'd', 'e')
val abcde = 'a' :: 'b' :: cde
println(abcde)

/*
【重要】
:: は List が持つメソッドであり、右被演算子である cde のメソッド。
通常、a * b のようにメソッドを演算子的に書いて使うとき、メソッドは
左被演算子のメソッドとして実行される。しかしメソッド末尾が : の場合は
右被演算子のメソッドとして呼び出される。
*/

// List の要素数を返す
println(oneTwoThreeFourFive.size)
println(oneTwoThreeFourFive.count(s => s > 3))

// 先頭の2要素を取り除いた List を返す
println(abcde.drop(2))
// 末尾の2要素を取り除いた List を返す
println(abcde.dropRight(2))

// List の中に指定した条件に合う要素があるかを返す
// 返り値は true or false
println(abcde.exists(s => s=='a'))
println(abcde.exists(s => s=="a"))

// 指定条件に合う要素のみ順に並べた List を返す
println(oneTwoThreeFourFive.filter(s => 1 < s && s <= 4))

// 指定条件に合わない要素のみを順に並べた List を返す
println(oneTwoThreeFourFive.filterNot(s => 1 < s && s <= 4))

// 全ての要素が指定条件を満たしているかを返す
// 返り値は true or false
println(oneTwoThreeFourFive.forall(s => 0 < s))
println(oneTwoThreeFourFive.forall(s => 10 < s))

// foreach
// 以下2文は同値
oneTwoThreeFourFive.foreach(s => print(s))
oneTwoThreeFourFive.foreach(print)

// 引数文字列で要素を連結した文字列を返す
println(abcde.mkString(", "))

// 各要素に同じ処理を施した List を返す
println(abcde.map(s => s + ".txt"))

// その他、head, init, isEmpty, last, tail, length, reverse などのメソッドもある

/*
【重要】
List クラスは :+ で末尾への要素の追加（python でいう append）ができる。
が、追加処理にかかる時間がリストの大きさに比例するため、あまり使用されない。
:: ならリストサイズによらず一定時間で実行できる。
=> 先頭から追加して行って最後に reverse した方が良い
*/



/********** タプル **********/
// タプルは List のように型が限定されない。整数と文字列の両方を格納したりできる。
// タプル名._1以上の数字 で要素にアクセスできる。
// メソッドから複数の値を返したいときにタプルで返すと便利。
// Java だとわざわざそれ用のクラスを定義したりしないといけない。
val triple = (35, "aiueo", 20.3)
println(triple._1)
println(triple._2)
println(triple._3)
// 要素番号は0でなく1から始まることに注意。



/********** 集合（Set） **********/
// 集合は mutable と immutable 両方がある。
// 3つのトレイトがあり、
//   scala.collection.Set
// 及びその下に
//   scala.collection.mutable.Set
//   scala.collection.immutable.Set
// がある。またこの2つのトレイトを拡張した具象集合クラス
//   scala.collection.mutable.HashSet
//   scala.collection.immutable.HashSet
// があり、具体的な集合クラスを明示的に表示したいときに使う。

// immutable な集合（デフォルトなのでインポート不要）
var jetSet = Set("Boeing", "Airbus")
jetSet += "Lear"
println(jetSet.contains("Cessna"))

// mutable な集合
import scala.collection.mutable
val movieSet = mutable.Set("Hitch", "Poltergeist")
movieSet += "Shrek"
println(movieSet)

// 明示的に集合クラスを指定
import scala.collection.immutable.HashSet
val hashSet = HashSet("Tomatoes", "Chilies")
println(hashSet + "Coriander")


/********** マップ（Map） **********/
// 集合と同様に mutable と immutable 両方がある。

// immutable な Map（デフォルトなのでインポート不要）
val romanNumeral = Map(1 -> "I", 2 -> "2")
println(romanNumeral(1))

// mutable な Map
import scala.collection.mutable
val treasureMap = mutable.Map[Int, String]()
treasureMap += (1 -> "Go to island.")
treasureMap += (2 -> "Find big X on ground.")
println(treasureMap(2))



/********** var を取り除こう **********/
// var が含まれているコードは命令型
// Scala では関数型のコードを推奨

// 命令型コード
def printArgs(args: Array[String]): Unit = {
    var i = 0
        while(i < args.length){
                println(args(i))
                i += 1
                    }
                    }
                    // 関数型コード
                    def printArgs2(args: Array[String]): Unit = {
                        for(arg <- args)
                                println(arg)
                                }

// このコードには副作用(println)があるので、削りたい。
// 返り値が Unit 型だと、「意味のある値を返さない」という
// ことなので確実に副作用を持つ。

// 最終版
// mkString は各要素を toString して引数文字列で連結する。
def formatArgs3(args: Array[String]) = args.mkString("\n")
println(formatArgs3(args))


/*
【重要】
明確なニーズや正当性があるときを除いて、
var のない、immutable な、副作用のないコーディングを心がける。
*/




/********** ファイルから行を読み出す **********/
import scala.io.Source
for(line <- Source.fromFile("test.txt").getLines())
    println(line.length + " " + line)



// テキストファイルから行の最大幅を計算
val lines = Source.fromFile("test.txt").getLines().toList
def widthOfLength(s: String) = s.length.toString.length

// var を使う方法
//var maxWidth = 0
//for(line <- lines)
//    maxWidth = maxWidth.max(widthOfLength(line))
//println("max width = " + maxWidth)

// var を使わない方法
// reduceLeft は、先頭の2要素に関数を適用し、
//その結果値と次の要素とで同じことを行って最終的な結果値を返す
val longestLine = lines.reduceLeft( (a, b) => if(a.length > b.length) a else b )
val maxWidth = widthOfLength(longestLine)
println("max width = " + maxWidth)

// さらに他の方法
// val longestLine = lines.maxBy(_.length)
// とか
// val longestLine = lines.reduce( (z, n) => if (n.length > z.length) n else z )


for(line <- lines){
    val numSpaces = maxWidth - widthOfLength(line)
        val padding = " " * numSpaces
            println(padding + line.length + " | " + line)