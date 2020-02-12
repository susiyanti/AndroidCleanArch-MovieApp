package com.susiyanti.movieapp.cache.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.susiyanti.movieapp.cache.db.MoviesDatabase
import com.susiyanti.movieapp.cache.test.factory.MovieDataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class CachedMoviesDaoTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MoviesDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @After fun closeDb() {
        database.close()
    }

    @Test fun cachedMoviesExistReturnsData() {
        val movies = with(MovieDataFactory) { listOf(makeCachedMovie(), makeCachedMovie()) }
        database.cachedMoviesDao().insertMovies(movies)
        val testObserver = database.cachedMoviesDao().cachedMoviesExist().test()
        testObserver.assertValue(1)
    }

    @Test fun `cachedMovies returns zero when cache empty`() {
        val testObserver = database.cachedMoviesDao().cachedMoviesExist().test()
        testObserver.assertValue(0)
    }

    @Test fun getMoviesReturnsData() {
        val movie = MovieDataFactory.makeCachedMovie()
        database.cachedMoviesDao().insertMovies(listOf(movie))
        val testObserver = database.cachedMoviesDao().getMovies().test()
        testObserver.assertValue(listOf(movie))
    }

    @Test fun deleteMoviesClearsData() {
        val project = MovieDataFactory.makeCachedMovie()
        database.cachedMoviesDao().insertMovies(listOf(project))
        database.cachedMoviesDao().deleteMovies()
        val testObserver = database.cachedMoviesDao().getMovies().test()
        testObserver.assertValue(emptyList())
    }

    @Test fun getFavoritedMoviesReturnsData() {
        val project = MovieDataFactory.makeCachedMovie()
        val favoritedMovie = MovieDataFactory.makeCachedFavoritedProject()
        database.cachedMoviesDao().insertMovies(listOf(project, favoritedMovie))
        val testObserver = database.cachedMoviesDao().getFavoritedMovies().test()
        testObserver.assertValue(listOf(favoritedMovie))
    }

    @Test fun setMovieAsBookmarkedSavesData() {
        val project = MovieDataFactory.makeCachedMovie()
        database.cachedMoviesDao().insertMovies(listOf(project))
        database.cachedMoviesDao().setFavoriteStatus(true, project.id)
        project.isFavorited = true
        val testObserver = database.cachedMoviesDao().getFavoritedMovies().test()
        testObserver.assertValue(listOf(project))
    }

    @Test fun setMovieAsNotBookmarkedSavesData() {
        val project = MovieDataFactory.makeCachedFavoritedProject()
        database.cachedMoviesDao().insertMovies(listOf(project))
        database.cachedMoviesDao().setFavoriteStatus(false, project.id)
        val testObserver = database.cachedMoviesDao().getFavoritedMovies().test()
        testObserver.assertValue(emptyList())
    }
}
