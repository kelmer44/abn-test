package com.kelmer.abn.foursquare.common.usecase

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class UseCase {
    private var lastDisposable: Disposable? = null
    private val disposables = CompositeDisposable()

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
}
