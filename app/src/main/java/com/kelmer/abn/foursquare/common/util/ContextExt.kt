package com.kelmer.abn.foursquare.common.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.kelmer.abn.foursquare.R


fun Context.handleError(it: Throwable, message: String = getString(R.string.error_generic)){
    Log.e("ERRORHANDLER", message, it)
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}