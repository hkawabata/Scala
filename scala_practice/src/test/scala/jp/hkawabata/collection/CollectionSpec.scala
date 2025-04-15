package jp.hkawabata.collection

import org.scalatest.WordSpec
import scala.collection.{immutable, mutable}
import scala.math

class CollectionSpec extends WordSpec {
  val T = 5

  "" in {
    val N = 100000

    val sizeList = List(3,4,5,6,7).map(math.pow(8, _).toInt)
    sizeList.foreach{
      size =>
        val values = 0 until size
        val keys = values.map(n => "v" + n.toString)

        val array = Array(values: _*)
        val imList = immutable.List(values: _*)
        val imVector = immutable.Vector(values: _*)
        val mListBuffer = mutable.ListBuffer(values: _*)
        val mArrayBuffer = mutable.ArrayBuffer(values: _*)

        benchmark(array)
    }
  }

  def benchmark(seq: Seq[Int]): (Double, Double) = {
    val timesHead = new mutable.ArrayBuffer[Long]
    val timesLast = new mutable.ArrayBuffer[Long]
    (1 to T).foreach{
      t =>
        val startHead = System.currentTimeMillis()
        print(seq.head)
        timesHead.append(System.currentTimeMillis() - startHead)

        val startLast = System.currentTimeMillis()
        print(seq.last)
        timesLast.append(System.currentTimeMillis() - startLast)
    }
  }
}
