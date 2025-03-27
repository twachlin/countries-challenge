package com.example.countries_challenge.domain.extensions

import com.example.countries_challenge.domain.utils.UseCaseResult
import com.example.countries_challenge.domain.utils.networkerror.NetworkError

fun <T> UseCaseResult<T>.getOrNull(): T? {
    return if (this is UseCaseResult.Success) data else null
}

inline fun <T> UseCaseResult<T>.onSuccess(action: (value: T) -> Unit): UseCaseResult<T> {
    if (this is UseCaseResult.Success) {
        action.invoke(this.data)
    }
    return this
}

inline fun <T> UseCaseResult<T>.onError(action: (error: NetworkError) -> Unit): UseCaseResult<T> {
    if (this is UseCaseResult.Error) {
        action.invoke(this.error)
    }
    return this
}