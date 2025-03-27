package com.example.countries_challenge.domain.utils

import com.example.countries_challenge.domain.utils.networkerror.NetworkError

sealed class UseCaseResult<out T> {
    class Success<out T>(val data: T): UseCaseResult<T>()
    class Error(val error: NetworkError): UseCaseResult<Nothing>()
}