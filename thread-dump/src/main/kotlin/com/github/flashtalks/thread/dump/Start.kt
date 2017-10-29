package com.github.flashtalks.thread.dump

import kotlin.concurrent.thread

fun main(args: Array<String>) {
    val toFile = ToFileFirstProducer()
    val toNetwork = ToNetworkFirstProducer()

    val toFileThread = thread { toFile.produce("abc") }
    val toNetworkThread = thread { toNetwork.produce("xyz") }

    toFileThread.join()
    toNetworkThread.join()

    println("All producers finished")
}