package com.kelmer.abn.foursquare.common.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.kelmer.abn.foursquare.common.resource.Resource
import org.reactivestreams.Publisher

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T) -> Unit) =
    observe(owner, Observer<T> { v ->
        v?.let {
            observer.invoke(v)
        }
    })

fun <T> LiveData<Resource<T>>.observeSuccess(
    owner: LifecycleOwner,
    observer: (Resource.Success<T>) -> Unit
) =
    observe(owner, Observer<Resource<T>> { v ->
        v?.let {
            if (it is Resource.Success) {
                observer.invoke(v as Resource.Success)
            }
        }
    })

fun <T> Publisher<T>.toLiveData() = LiveDataReactiveStreams.fromPublisher(this) as LiveData<T>

fun <X, Y> LiveData<X>.map(transformer: (X) -> Y): LiveData<Y> =
    Transformations.map(this) { transformer(it) }

fun <X, Y> LiveData<X>.switchMap(transformer: (X) -> LiveData<Y>): LiveData<Y> =
    Transformations
        .switchMap(this) { transformer(it) }
