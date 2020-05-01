package com.kelmer.abn.foursquare.common.viewmodel

import androidx.lifecycle.ViewModel
import com.kelmer.abn.foursquare.common.usecase.IUseCase
import com.kelmer.abn.foursquare.common.usecase.UseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class UseCaseViewModel(vararg useCases: IUseCase) : ViewModel() {

    private val useCaseList = useCases.asList()
    private val disposables: CompositeDisposable = CompositeDisposable()

    fun Disposable.track() {
        disposables.add(this)
    }

    override fun onCleared() {
        useCaseList.forEach { it.dispose() }
        disposables.dispose()
        super.onCleared()
    }

}