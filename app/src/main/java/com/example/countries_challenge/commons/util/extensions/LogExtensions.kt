package com.example.countries_challenge.commons.util.extensions

import android.util.Log

@JvmOverloads
fun Any.loge(message: String?, throwable: Throwable? = null) {
    loge(javaClass.name, message, throwable)
}

@JvmOverloads
fun loge(tag: String?, message: String?, throwable: Throwable? = null) {
    Log.e(tag, message, throwable)
}