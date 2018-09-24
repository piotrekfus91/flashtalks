package com.github.flashtalks.bloom

class Container(private val canContain: CanContain) {
    private val entries: MutableList<Entry> = mutableListOf()

    fun add(entry: Entry) {
        canContain.add(entry.id)
        entries.add(entry)
    }

    fun get(id: Long): List<Entry> = entries.filter { it.id == id }

    fun canContain(id: Long) = canContain.canContain(id)
}

interface CanContain {
    fun canContain(id: Long): Boolean
    fun add(id: Long)
}

class AlwaysCanContain : CanContain {
    override fun canContain(id: Long): Boolean = true
    override fun add(id: Long) = Unit
}

class BloomFilter : CanContain {
    private val bits: Array<Int> = Array(32768) { 0 }

    override fun add(id: Long) {
        val hash = hash(id)
        bits[hash.toInt()] = 1
    }

    override fun canContain(id: Long): Boolean {
        val hash = hash(id)
        return bits[hash.toInt()] == 1
    }

    private fun hash(id: Long) = Math.abs(id) % bits.size

}
