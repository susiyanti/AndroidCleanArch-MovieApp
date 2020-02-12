package com.susiyanti.mobileui.favorited

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.susiyanti.movieapp.domain.model.Movie
import com.susiyanti.mobileui.R
import com.susiyanti.mobileui.TestApplication
import com.susiyanti.mobileui.ext.ActivityTestRule
import com.susiyanti.mobileui.test.factory.MovieFactory
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = TestApplication::class, sdk = [28])
class FavoritedActivityTest {
    @get:Rule val activity = ActivityTestRule<FavoritedActivity>(launchActivity = false)

    @Test
    fun activityLaunches() {
        stubMoviesRepositoryGetBookmarkedMovies(Observable.just(
                MovieFactory.makeMovieList(1)))
        activity.launchActivity(null)
    }

    @Test
    fun favoritedMoviesDisplay() {
        val movies = MovieFactory.makeMovieList(10)
        stubMoviesRepositoryGetBookmarkedMovies(Observable.just(movies))
        activity.launchActivity(null)
        movies.forEachIndexed { index, movie ->
            Espresso.onView(ViewMatchers.withId(R.id.recycler_view))
                    .perform(RecyclerViewActions
                            .scrollToPosition<FavoritedAdapter.ViewHolder>(index))
            Espresso.onView(ViewMatchers.withId(R.id.recycler_view))
                    .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(movie.title))))
        }
    }

    private fun stubMoviesRepositoryGetBookmarkedMovies(observable: Observable<List<Movie>>) {
        val repoMock = TestApplication.appComponent().moviesRepository()
        whenever(repoMock.getFavoritedMovies()) doReturn observable
    }
}