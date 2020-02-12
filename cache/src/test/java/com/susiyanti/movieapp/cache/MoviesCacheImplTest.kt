package com.susiyanti.movieapp.cache

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.susiyanti.movieapp.cache.db.MoviesDatabase
import com.susiyanti.movieapp.cache.mapper.CachedMovieMapper
import com.susiyanti.movieapp.cache.test.factory.MovieDataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)

class MoviesCacheImplTest {

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MoviesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    private val entityMapper = CachedMovieMapper()
    private val cache = MoviesCacheImpl(database, entityMapper)

    @After fun clearDb() {
        database.close()
    }

    @Test fun clearTablesCompletes() {
        val testObserver = cache.clearMovies().test()
        testObserver.assertComplete()
    }

    @Test fun saveMoviesCompletes() {
        val movies = listOf(MovieDataFactory.makeMovieEntity())
        val testObserver = cache.saveMovies(movies).test()
        testObserver.assertComplete()
    }

    @Test fun getMoviesReturnsData() {
        val movies = listOf(MovieDataFactory.makeMovieEntity())
        cache.saveMovies(movies).test()
        val testObserver = cache.getMovies().test()
        testObserver.assertValue(movies)
    }

    @Test fun getFavoritedMoviesReturnsData() {
        val favoritedMovie = MovieDataFactory.makeFavoritedMovieEntity()
        val movies = listOf(MovieDataFactory.makeMovieEntity(),
                favoritedMovie)
        cache.saveMovies(movies).test()
        val testObserver = cache.getFavoritedMovies().test()
        testObserver.assertValue(listOf(favoritedMovie))
    }

    @Test fun setMovieAsFavoritedCompletes() {
        val movies = listOf(MovieDataFactory.makeMovieEntity())
        cache.saveMovies(movies).test()
        val testObserver = cache.setMovieAsFavorited(movies[0].id).test()
        testObserver.assertComplete()
    }

    @Test fun setMovieAsNotFavoritedCompletes() {
        val movies = listOf(MovieDataFactory.makeFavoritedMovieEntity())
        cache.saveMovies(movies).test()
        val testObserver = cache.setMovieAsNotFavorited(movies[0].id).test()
        testObserver.assertComplete()
    }

    @Test fun areMoviesCacheReturnsData() {
        val Movies = listOf(MovieDataFactory.makeMovieEntity())
        cache.saveMovies(Movies).test()
        val testObserver = cache.areMoviesCached().test()
        testObserver.assertValue(true)
    }

    @Test fun areMoviesCacheNotCachedReturnsData() {
        val testObserver = cache.areMoviesCached().test()
        testObserver.assertValue(false)
    }

    @Test fun setLastCacheTimeCompletes() {
        val testObserver = cache.setLastCacheTime(1000L).test()
        testObserver.assertComplete()
    }

    @Test fun isMoviesCacheExpiredReturnsNotExpired() {
        cache.setLastCacheTime(System.currentTimeMillis()).test()
        val testObserver = cache.isMoviesCacheExpired().test()
        println(testObserver.values())
        testObserver.assertValue(false)
    }
}
