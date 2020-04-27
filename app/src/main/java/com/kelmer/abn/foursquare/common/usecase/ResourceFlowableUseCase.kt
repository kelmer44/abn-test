package com.kelmer.abn.foursquare.common.usecase

import com.kelmer.abn.foursquare.common.resource.Resource
import com.kelmer.abn.foursquare.common.resource.toResource
import com.kelmer.abn.foursquare.common.util.NetworkInteractor
import com.kelmer.abn.foursquare.common.util.SchedulerProvider
import io.reactivex.Flowable
import io.reactivex.rxkotlin.subscribeBy

abstract class ResourceFlowableUseCase<T : Any, in Params>(
    private val schedulerProvider: SchedulerProvider, networkInteractor: NetworkInteractor
) : UseCase(networkInteractor) {


    internal abstract fun buildUseCase(params: Params): Flowable<T>

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
}
