package com.susiyanti.movieapp.cache.test.factory

import com.susiyanti.movieapp.cache.model.Config

object ConfigDataFactory {
    fun makeCachedConfig(): Config {
        return Config(DataFactory.randomInt(), DataFactory.randomLong())
    }
}
