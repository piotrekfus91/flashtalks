package com.github.flashtalks.thread.dump

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

object GlobalLock {
    private val fileLock = ReentrantLock()
    private val networkLock = ReentrantLock()

    fun fileLock() {
        fileLock.lock()
    }

    fun fileUnlock() {
        fileLock.unlock()
    }

    fun networkLock() {
        networkLock.lock()
    }

    fun networkUnlock() {
        networkLock.unlock()
    }
}