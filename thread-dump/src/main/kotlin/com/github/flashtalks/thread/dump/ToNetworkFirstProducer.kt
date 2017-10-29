package com.github.flashtalks.thread.dump

class ToNetworkFirstProducer : Producer {
    override fun produce(value: String) {
        GlobalLock.networkLock()
        toNetwork(value)
        GlobalLock.fileLock()
        toFile(value)
        GlobalLock.networkUnlock()
        GlobalLock.fileUnlock()
    }

    private fun toFile(value: String) = println("${this.javaClass.simpleName}: To file: $value")

    private fun toNetwork(value: String) {
        println("${this.javaClass.simpleName}: To network: $value")
        Thread.sleep(1000)
    }
}