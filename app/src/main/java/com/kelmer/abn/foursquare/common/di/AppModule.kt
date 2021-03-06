package com.kelmer.abn.foursquare.common.di

import android.content.Context
import android.net.ConnectivityManager
import com.kelmer.abn.foursquare.common.util.ApplicationSchedulerProvider
import com.kelmer.abn.foursquare.common.util.NetworkInteractor
import com.kelmer.abn.foursquare.common.util.NetworkInteractorImpl
import com.kelmer.abn.foursquare.common.util.SchedulerProvider
import com.kelmer.abn.foursquare.ui.detail.DetailViewModel
import com.kelmer.abn.foursquare.ui.list.ListViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        ListViewModel(get())
    }

    viewModel {
        DetailViewModel(get())
    }

    single<SchedulerProvider> { ApplicationSchedulerProvider() }

    single<Moshi> {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    single<ConnectivityManager> {
        androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    single<NetworkInteractor> { NetworkInteractorImpl(get()) }

}

