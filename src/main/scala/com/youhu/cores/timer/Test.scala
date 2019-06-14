package com.youhu.cores.timer

import scala.collection.mutable

object Test {
  var set: mutable.Set[String] = scala.collection.mutable.Set[String]()

  def leijia(str: String): Unit = {
    set.add(str)
  }

  def qingkong(): Unit = {
    set.clear()
  }

  def print(): Unit = {
    set.foreach(println)
  }

}
