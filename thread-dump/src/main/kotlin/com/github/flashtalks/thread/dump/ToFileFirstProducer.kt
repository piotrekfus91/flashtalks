package com.github.flashtalks.thread.dump

class ToFileFirstProducer : Producer {
    override fun produce(value: String) {
        GlobalLock.fileLock()
        toFile(value)
        GlobalLock.networkLock()
        toNetwork(value)
        GlobalLock.fileUnlock()
        GlobalLock.networkUnlock()
    }

    private fun toFile(value: String) {
        println("${this.javaClass.simpleName}: To file: $value")
        Thread.sleep(1000)
    }

    private fun toNetwork(value: String) = println("${this.javaClass.simpleName}: To network: $value")
}