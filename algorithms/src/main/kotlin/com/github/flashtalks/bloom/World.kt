package com.github.flashtalks.bloom

import java.util.Random

class World {
    private val containers: MutableList<Container> = mutableListOf()

    fun addContainer(container: Container) = containers.add(container)
    fun addEntry(entry: Entry) = containers.random()?.add(entry) ?: throw RuntimeException("empty list")

    fun get(id: Long): List<Entry?> = containers.filter { it.canContain(id) }.flatMap { it.get(id).toList() }
}

fun <E> List<E>.random() = if (size > 0) get(Random().nextInt(size)) else null
