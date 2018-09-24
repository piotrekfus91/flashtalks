package com.github.flashtalks.bloom

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.util.Random

class BloomTest : Spek({
    val random = Random()
    val containersCount = 10
    val entriesCount = 50000
    describe("should find entry in the world") {
        val entries = (1 .. entriesCount).map { Entry(random.nextLong()) }
        on("should not use bloom filter") {
            val world = World()
            repeat(containersCount) { world.addContainer(Container(AlwaysCanContain())) }
            entries.forEach { world.addEntry(it) }
            it("should find each and every one") {
                entries.forEach {
                    world.get(it.id)!!
                }
            }
        }

        on("should use bloom filter") {
            val world = World()
            repeat(containersCount) { world.addContainer(Container(BloomFilter())) }
            entries.forEach { world.addEntry(it) }
            it("should find each and every one") {
                entries.forEach {
                    world.get(it.id)!!
                }
            }
        }
    }
})
