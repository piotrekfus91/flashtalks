package com.github.distlocks

import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.CuratorFrameworkFactory
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex
import org.apache.curator.retry.RetryNTimes
import org.kottpd.HttpRequest
import org.kottpd.Server
import java.lang.Thread.sleep

object DistLocksApp {
    @JvmStatic
    fun main(args: Array<String>) {
        val curator = getCurator()

        val server = Server(args[0].toInt())
        server.get("/lock/.*") { req, _ -> handleResource(req, curator) }
        server.start()
    }

    private fun handleResource(req: HttpRequest, curator: CuratorFramework): String {
        val resource = extractResource(req)

        val lock = InterProcessSemaphoreMutex(curator, "/mutex/process/$resource")

        lock.acquire()
        try {
            sleep(1000)
        } finally {
            lock.release()
        }
        return "finished"
    }

    private fun getCurator(): CuratorFramework {
        val retryPolicy = RetryNTimes(3, 300)
        return CuratorFrameworkFactory.newClient("localhost:2181", retryPolicy)!!.apply { start() }
    }

    private fun extractResource(req: HttpRequest): String = req.url.split("/").last()

}
