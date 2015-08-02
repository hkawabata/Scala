// 改行ありで標準出力
println("改行あり")
// 改行なしで標準出力
print("改行なし")

// 定数の宣言
val msg_val = "constant"
val int_val: Int = 100
// 変数の宣言
var msg_var = "variable"
var int_var: Int = 1000

// 関数の定義
def larger(x: Int, y: Int): Int = {
	if (x < y) y
	else x
}
println(larger(int_val, int_var))

// while 文
var i = 0
while (i < args.length){
	println(args(i))
	i += 1
}

// foreach 文
args.foreach(arg => println(arg))

// for 式
// パラメータ arg は毎回初期化される定数なので、ループないで値を変更できない
for (arg <- args) println(arg)