package com.susiyanti.movieapp.domain.interactor.favorite

import com.susiyanti.movieapp.domain.executor.PostExecutionThread
import com.susiyanti.movieapp.domain.interactor.CompletableUseCase
import com.susiyanti.movieapp.domain.repository.MoviesRepository
import io.reactivex.Completable
import javax.inject.Inject

class UnFavoriteMovie @Inject constructor(
        private val moviesRepository: MoviesRepository,
        postExecutionThread: PostExecutionThread)
    : CompletableUseCase<UnFavoriteMovie.Params>(postExecutionThread) {

    override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params can't be null!")
        return moviesRepository.unfavoriteMovie(params.projectId)
    }

    data class Params constructor(val projectId: String) {
        companion object {
            fun forProject(projectId: String): Params {
                return Params(projectId)
            }
        }
    }
}