package com.susiyanti.mobileui.browse

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
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

@RunWith(AndroidJUnit4::class)
class BrowseActivityUITest {

    @get:Rule val activity = ActivityTestRule<BrowseActivity>(launchActivity = false)

    @Test fun activityLaunches() {
        stubProjectsRepositoryGetProjects(
            Observable.just(
                listOf(
                    MovieFactory.makeMovie()
                )
            )
        )
        activity.launchActivity(null)
    }

    @Test fun projectsDisplay() {
        val projects = MovieFactory.makeMovieList(10)
        stubProjectsRepositoryGetProjects(Observable.just(projects))
        activity.launchActivity(null)

        projects.forEachIndexed { index, project ->
            onView(withId(R.id.recycler_view))
                .perform(
                    RecyclerViewActions
                        .scrollToPosition<BrowseAdapter.ViewHolder>(index)
                )

            onView(withId(R.id.recycler_view))
                .check(matches(hasDescendant(withText(project.title))))
        }
    }

    private fun stubProjectsRepositoryGetProjects(observable: Observable<List<Movie>>) {
        val repoMock = TestApplication.appComponent().moviesRepository()
        whenever(repoMock.getMovies()) doReturn observable
    }

}