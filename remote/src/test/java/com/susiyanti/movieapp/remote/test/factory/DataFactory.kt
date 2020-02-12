package com.susiyanti.movieapp.remote.test.factory

import java.util.*
import java.util.concurrent.ThreadLocalRandom

object DataFactory {
    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomInt(): Int {
        return ThreadLocalRandom.current().nextInt(0, 1000 + 1)
    }

    fun randomDouble(): Double {
        return ThreadLocalRandom.current().nextDouble(0.0,1000.0+1)
    }

    fun randomBoolean(): Boolean {
        return Math.random() < 0.5
    }
}