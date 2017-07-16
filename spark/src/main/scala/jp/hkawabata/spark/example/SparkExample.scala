package jp.hkawabata.spark.example

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by kawabatahiroto on 2017/07/16.
  */
object SparkExample {
  def main (args: Array[String]) {
    val conf = new SparkConf().setAppName(getClass.getSimpleName)
    val sc = new SparkContext()

    val input = List("apple", "grape", "lemon", "orange", "peach", "lemon", "orange", "peach", "lemon", "orange", "apple")
    val rdd = sc.makeRDD(input)
    val cntRDD = rdd.filter(_.contains("p")).map(fruit => (fruit, 1)).reduceByKey(_ + _)
    cntRDD.saveAsTextFile("output")
  }
}
