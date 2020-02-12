package com.susiyanti.movieapp.domain.interactor.browse

import com.susiyanti.movieapp.domain.executor.PostExecutionThread
import com.susiyanti.movieapp.domain.interactor.ObservableUseCase
import com.susiyanti.movieapp.domain.model.Movie
import com.susiyanti.movieapp.domain.repository.MoviesRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetMovies @Inject constructor(
        private val moviesRepository: MoviesRepository,
        postExecutionThread: PostExecutionThread)
    : ObservableUseCase<List<Movie>, Nothing?>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Nothing?): Observable<List<Movie>> {
        return moviesRepository.getMovies()
    }
}
