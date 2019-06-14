package com.youhu.cores.timer

object Test2 {
  def main(args: Array[String]): Unit = {
    for (i <- 1 to 10) {
      Test.leijia(i.toString)
    }
    Test.print()
    println("--------")
    Test.leijia("a")
    Test.leijia("b")
    Test.leijia("c")

    Test.print()
  }
}
