package com.github.flashtalks.kotlinlang

import java.io.File
import java.net.URL

class Receivers {
    val printYearsSinceBecomingAdults by lazy {
        IntRange(0, 100)
                .filter { it >= 18 }
                .map { it - 18 }
                .forEach { println(it) }
    }

    ///////// beginning //////////////////

    fun plain(): String {
        val dir = File("/tmp/path")
        dir.mkdir()
        val file = File(dir, "file")
        file.setExecutable(true)
        val url = URL("file://${file.absolutePath}")
        return if (File(url.path).exists()) {
            url.path
        } else {
            "not found simple"
        }
    }

    ///////// extension functions /////////////

    fun String.printLength() = println("length is ${this.length}")

    fun String.printLength2() = println("length is $length")

    fun String.shoutIt(): String = this + "!"

    fun String.shoutItLoudly(times: Int) = this + "!".repeat(times)

    fun extensionsTesting() {
        "abc".printLength()
        "abc".printLength2()
        println("abc".shoutIt())
        println("abc".shoutItLoudly(3))
    }

    fun withExtensionFunctions(): String  = File("/tmp/path")
            .also { it.mkdir() }
            .let {
                File(it, "file").also {
                    it.setExecutable(true)
                }
            }
            .let { it.absolutePath }
            .let { URL("file://$it") }
            .takeIf { File(it.path).exists() }
            ?.path ?: "not found extensions"

    ///////////// receivers ///////////////////
    val printLength: String.() -> Unit = { println("length is ${this.length}") }

    val printLength2: String.() -> Unit = { println("length is $length") }

    val shoutIt: String.() -> String = { this + "!" }

    val shoutItLoudly: String.(times: Int) -> String = { times -> this + "!".repeat(times)}

    val shoutItLoudly2: String.(times: Int) -> String = { this + "!".repeat(it)}

    val countLength: String.() -> Int = { this.length }

    fun mapMe(str: String, f: String.() -> Int): Int {
        return str.f()
    }

    fun receiversTesting() {
        printLength("abc")
        printLength2("abc")
        println(shoutIt("abc"))
        println(shoutItLoudly("abc", 3))
        println(shoutItLoudly2("abc", 5))
        println(mapMe("abcdef", countLength))
    }

    fun withReceivers(): String = File("/tmp/path")
            .apply { mkdir() }
            .run {
                File(this, "file").apply {
                    setExecutable(true)
                }
            }
            .run { absolutePath }
            .run { URL("file://$this") }
            .takeIf { File(it.path).exists() }
            ?.run { path } ?: "not found receivers"

    ////////////////// together ///////////////////////

    fun together(): String = File("/tmp/path")
            .apply { mkdir() }
            .run {
                File(this, "file").apply {
                    setExecutable(true)
                }
            }
            .absolutePath
            .let { URL("file://$it") }
            .takeIf { File(it.path).exists() }
            ?.path ?: "not found together"
}

fun main(args: Array<String>) {
    val receivers = Receivers()
    println(receivers.plain())
//    receivers.extensionsTesting()
//    println(receivers.withExtensionFunctions())
//    receivers.receiversTesting()
//    println(receivers.withReceivers())
//    println(receivers.together())
}
