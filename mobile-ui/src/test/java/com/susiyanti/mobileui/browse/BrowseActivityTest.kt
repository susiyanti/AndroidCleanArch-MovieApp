package com.susiyanti.mobileui.browse

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
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
class BrowseActivityTest {

    @get:Rule val activity = ActivityTestRule<BrowseActivity>(launchActivity = false)

    @Test fun activityLaunches() {
        stubMoviesRepositoryGetMovies(
            Observable.just(
                listOf(
                    MovieFactory.makeMovie()
                )
            )
        )
        activity.launchActivity(null)
    }

    @Test fun moviesDisplay() {
        val movies = MovieFactory.makeMovieList(10)
        stubMoviesRepositoryGetMovies(Observable.just(movies))
        activity.launchActivity(null)

        movies.forEachIndexed { index, movie ->
            onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToPosition<BrowseAdapter.ViewHolder>(index))

            onView(withId(R.id.recycler_view))
                .check(matches(ViewMatchers.hasDescendant(withText(movie.title))))
        }
    }

    private fun stubMoviesRepositoryGetMovies(observable: Observable<List<Movie>>) {
        val repoMock = TestApplication.appComponent().moviesRepository()
        whenever(repoMock.getMovies()) doReturn observable
    }
}
