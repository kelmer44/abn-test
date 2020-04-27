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

abstract class UseCase : KoinComponent {
    private var lastDisposable: Disposable? = null
    private val disposables = CompositeDisposable()

    private val networkInteractor by inject<NetworkInteractor>()

    fun disposeLast() {
        lastDisposable?.let {
            if (!it.isDisposed) it.dispose()
        }
    }

    fun dispose() {
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
                                Log.e("NO NETWORK", "Reacehd max of retries! throwing error down the chain : $error")
                                throw error
                            } else {
                                Log.e("NO NETWORK", "Retry number $retryCount")
                                retryCount
                            }
                        } else {
                            Log.e("NO NETWORK", "Not a network unavailable error : $error")
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
