package com.kelmer.abn.foursquare.common.resource

import androidx.lifecycle.MutableLiveData
import com.kelmer.abn.foursquare.common.util.SchedulerProvider
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

fun <T> Flowable<T>.toResource(schedulerProvider: SchedulerProvider): Flowable<Resource<T>> {
    return compose { item ->
        item
            .map { Resource.success(it) }
            .onErrorReturn { e -> Resource.failure(e) }
            .ioUi(schedulerProvider)
            .startWith(Resource.inProgress())
    }
}

fun <T> Single<T>.toResource(schedulerProvider: SchedulerProvider): Flowable<Resource<T>> {
    return toFlowable().toResource(schedulerProvider)
}

fun Completable.toResource(schedulerProvider: SchedulerProvider): Flowable<Resource<Unit>> {
    return toSingleDefault(Unit).toFlowable().toResource(schedulerProvider)
}

fun <T> Flowable<Resource<T>>.withLiveData(liveData: MutableLiveData<Resource<T>>): Disposable {
    return this.subscribeBy(
        onNext = {
            liveData.value = it
        })
}

fun <T> Single<T>.ioUi(schedulerProvider: SchedulerProvider): Single<T> {
    return this.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
}

fun <T> Observable<T>.ioUi(schedulerProvider: SchedulerProvider): Observable<T> {
    return this.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
}

fun <T> Flowable<T>.ioUi(schedulerProvider: SchedulerProvider): Flowable<T> {
    return this.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
}

fun Completable.ioUi(schedulerProvider: SchedulerProvider): Completable {
    return this.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
}

fun <T> Observable<T>.toResource(schedulerProvider: SchedulerProvider, backpressureStrategy: BackpressureStrategy = BackpressureStrategy.LATEST): Flowable<Resource<T>> {
    return toFlowable(backpressureStrategy).toResource(schedulerProvider)
}
