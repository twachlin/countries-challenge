package com.example.countries_challenge.data.util.net.interceptor

import android.content.Context
import com.example.countries_challenge.data.util.extensions.isOnline
import com.example.countries_challenge.domain.exceptions.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.isOnline()) {
            throw NoConnectivityException(message = "No connected to internet")
        }
        return chain.proceed(chain.request())
    }
}