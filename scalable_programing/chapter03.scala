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

