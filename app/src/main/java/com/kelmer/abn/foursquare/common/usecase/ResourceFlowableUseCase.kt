package com.kelmer.abn.foursquare.common.usecase

import android.util.Log
import com.kelmer.abn.foursquare.common.resource.Resource
import com.kelmer.abn.foursquare.common.util.SchedulerProvider
import io.reactivex.Flowable
import io.reactivex.rxkotlin.subscribeBy
import com.kelmer.abn.foursquare.common.resource.toResource
import com.kelmer.abn.foursquare.common.util.NetworkInteractor
import io.reactivex.FlowableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.functions.BiFunction
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

abstract class ResourceFlowableUseCase<T : Any, in Params>(private val schedulerProvider: SchedulerProvider) : UseCase() {


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
