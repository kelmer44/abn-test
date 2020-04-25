package com.kelmer.abn.foursquare

import android.app.Application
import com.kelmer.abn.foursquare.common.di.DIHelper

class ABNFoursquareApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DIHelper.init(context = this)
    }
}