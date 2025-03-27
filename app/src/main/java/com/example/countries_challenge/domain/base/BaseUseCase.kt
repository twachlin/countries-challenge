package com.example.countries_challenge.domain.base

import com.example.countries_challenge.commons.util.extensions.loge
import com.example.countries_challenge.domain.utils.UseCaseResult
import com.example.countries_challenge.domain.utils.networkerror.NetworkError

abstract class BaseUseCase<Result, Params> {

    protected abstract suspend fun buildUseCase(params: Params): UseCaseResult<Result>

    open suspend fun execute(params: Params) =
        try {
            buildUseCase(params)
        } catch (error: Throwable) {
            logError(error)
            UseCaseResult.Error(NetworkError.UndefinedError(cause = error))
        }

    open suspend fun logError(error: Throwable) {
        loge(error.message, error)
    }
}