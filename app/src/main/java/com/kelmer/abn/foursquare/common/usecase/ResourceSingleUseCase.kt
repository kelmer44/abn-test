package com.kelmer.abn.foursquare.common.usecase

import com.kelmer.abn.foursquare.common.resource.Resource
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import com.kelmer.abn.foursquare.common.resource.toResource
import com.kelmer.abn.foursquare.common.util.NetworkInteractor
import com.kelmer.abn.foursquare.common.util.SchedulerProvider

abstract class ResourceSingleUseCase<T : Any, in Params>(private val schedulerProvider: SchedulerProvider,  networkInteractor: NetworkInteractor) : UseCase(networkInteractor) {
    internal abstract fun buildUseCase(params: Params): Single<T>

    fun execute(
        params: Params,
        onNext: ((Resource<T>) -> Unit)
    ) {
        disposeLast()
        buildUseCase(params)
            .toResource(schedulerProvider)
            .subscribeBy(
                onNext = onNext
            )
            .track()
    }

    protected fun <U : Any?> Single<U>.continueIO(): Single<U> = this.observeOn(schedulerProvider.io())
}
