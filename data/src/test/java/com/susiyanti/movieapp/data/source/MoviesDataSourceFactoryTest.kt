package com.susiyanti.movieapp.data.source

import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import kotlin.test.assertEquals

class MoviesDataSourceFactoryTest {

    private val cacheStore = mock<MoviesCacheDataSource>()
    private val remoteStore = mock<MoviesRemoteDataSource>()
    private val factory = MoviesDataSourceFactory(cacheStore, remoteStore)

    @Test fun getDataSourceReturnsRemoteStoreWhenCacheExpired() {
        assertEquals(remoteStore, factory.getDataSource(true, true))
    }

    @Test fun getDataSourceReturnsRemoteStoreWhenProjectsNotCached() {
        assertEquals(remoteStore, factory.getDataSource(false, false))
    }

    @Test fun getDataSourceReturnsCacheStore() {
        assertEquals(cacheStore, factory.getDataSource(true, false))
    }

    @Test fun getCacheDataSourceReturnsCacheStore() {
        assertEquals(cacheStore, factory.getCacheDataSource())
    }
}
