package com.kelmer.abn.foursquare.common.util

import io.reactivex.*

interface NetworkInteractor {

    fun hasNetworkConnection(): Boolean

    fun hasNetworkConnectionCompletable(): Completable


    class NetworkUnavailableException : Throwable("No network available!")

    fun <T> flowable() : FlowableTransformer<T, T> {
        return  FlowableTransformer{observable->
            this.hasNetworkConnectionCompletable().andThen(observable)
        }
    }

    fun <T> observable() : ObservableTransformer<T, T> {
        return  ObservableTransformer{observable->
            this.hasNetworkConnectionCompletable().andThen(observable)
        }
    }

    fun <T> single() : SingleTransformer<T, T> {
        return  SingleTransformer{observable->
            this.hasNetworkConnectionCompletable().andThen(observable)
        }
    }
    fun completable() : CompletableTransformer {
        return CompletableTransformer { completable->
            this.hasNetworkConnectionCompletable().andThen(completable)
        }
    }

}