package com.github.distlocks

import com.github.kittinunf.fuel.httpGet
import org.amshove.kluent.`should be greater or equal to`
import org.amshove.kluent.`should be less than`
import org.amshove.kluent.`should equal`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.util.concurrent.Callable
import java.util.concurrent.Executors

object DistLocksAppTest : Spek({
    val executorService = Executors.newFixedThreadPool(2)

    describe("DistLocksApp") {
        on("selecting different resources on same server") {
            val start = System.currentTimeMillis()
            val jobA = executorService.submit(Callable<Long> {
                fetchResource("http://localhost:1234/lock/a")
            })
            val jobB = executorService.submit(Callable<Long> {
                fetchResource("http://localhost:1234/lock/b")
            })

            it("should act after 1 second") {
                (jobA.get() - start) `should be less than` 1500
                (jobB.get() - start) `should be less than` 1500
            }
        }

        on("selecting different resources on various server") {
            val start = System.currentTimeMillis()
            val jobA = executorService.submit(Callable<Long> {
                fetchResource("http://localhost:1234/lock/a")
            })
            val jobB = executorService.submit(Callable<Long> {
                fetchResource("http://localhost:12345/lock/b")
            })

            it("should act after 1 second") {
                (jobA.get() - start) `should be less than` 1500
                (jobB.get() - start) `should be less than` 1500
            }
        }

        on("selecting same resources on same server") {
            val start = System.currentTimeMillis()
            val jobA = executorService.submit(Callable<Long> {
                fetchResource("http://localhost:1234/lock/a")
            })
            val jobB = executorService.submit(Callable<Long> {
                fetchResource("http://localhost:1234/lock/a")
            })

            it("should act after 2 seconds") {
                val times = listOf(jobA.get() - start, jobB.get() - start).sorted()
                times[0] `should be less than` 1800
                times[1] `should be greater or equal to`  2000
            }
        }

        on("selecting same resources on various server") {
            val start = System.currentTimeMillis()
            val jobA = executorService.submit(Callable<Long> {
                fetchResource("http://localhost:1234/lock/a")
            })
            val jobB = executorService.submit(Callable<Long> {
                fetchResource("http://localhost:12345/lock/a")
            })

            it("should act after 2 seconds") {
                val times = listOf(jobA.get() - start, jobB.get() - start).sorted()
                times[0] `should be less than` 1800
                times[1] `should be greater or equal to`  2000
            }
        }

        afterGroup {
            executorService.shutdown()
        }
    }
})

private fun fetchResource(url: String): Long {
    val response = url.httpGet().responseString()
    response.third.component1() `should equal` "finished"
    return System.currentTimeMillis()
}
