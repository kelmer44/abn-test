package com.kelmer.abn.foursquare.common.usecase

import android.util.Log
import com.kelmer.abn.foursquare.common.resource.Resource
import com.kelmer.abn.foursquare.common.resource.toResource
import com.kelmer.abn.foursquare.common.util.NetworkInteractor
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

abstract class UseCase(private val networkInteractor: NetworkInteractor) : IUseCase, KoinComponent {
    private var lastDisposable: Disposable? = null
    private val disposables = CompositeDisposable()

    fun disposeLast() {
        lastDisposable?.let {
            if (!it.isDisposed) it.dispose()
        }
    }

    override fun dispose() {
        disposables.clear()
    }

    fun Disposable.track() {
        disposables.add(this)
        lastDisposable = this
    }

    private val RETRIES_LIMIT = 10
    fun <T> noNetwork() : FlowableTransformer<T, T> {
        return FlowableTransformer<T, T> {
            it.retryWhen {
                    errors: Flowable<Throwable> ->
                errors.zipWith(
                    Flowable.range(1, RETRIES_LIMIT + 1),
                    BiFunction<Throwable, Int, Int> { error: Throwable, retryCount: Int ->
                        if (noNetworkAvailableError(error, networkInteractor)) {
                            if (retryCount > RETRIES_LIMIT) {
                                throw error
                            } else {
                                retryCount
                            }
                        } else {
                            throw error
                        }

                    }
                ).flatMap { retryCount: Int ->
                    Flowable.timer(
                        Math.pow(2.toDouble(), retryCount.toDouble()).toLong(),
                        TimeUnit.SECONDS
                    )
                }


            }
        }
    }
    private fun noNetworkAvailableError(error: Throwable,
                                        networkInteractor: NetworkInteractor
    ): Boolean =
        error is UnknownHostException || !networkInteractor.hasNetworkConnection()


}
