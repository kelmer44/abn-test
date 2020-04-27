package com.kelmer.abn.foursquare.common.util

import android.net.ConnectivityManager
import android.net.Network
import io.reactivex.Completable
import io.reactivex.subjects.BehaviorSubject

class NetworkInteractorImpl constructor(
    private val connectivityManager: ConnectivityManager
) : NetworkInteractor {

    override fun hasNetworkConnection(): Boolean =
        connectivityManager.activeNetworkInfo?.isConnectedOrConnecting ?: false

    override fun hasNetworkConnectionCompletable(): Completable =
        if (hasNetworkConnection()) {
            Completable.complete()
        } else {
            Completable.error { NetworkInteractor.NetworkUnavailableException() }
        }
}