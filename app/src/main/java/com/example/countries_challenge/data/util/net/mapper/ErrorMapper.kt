package com.example.countries_challenge.data.util.net.mapper

import com.example.countries_challenge.domain.utils.UseCaseResult
import com.example.countries_challenge.domain.utils.networkerror.NetworkError
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object ErrorMapper {

    fun fromExceptionToUseCaseResultError(e: Exception, errorBody: String?): UseCaseResult.Error {
        return UseCaseResult.Error(
            when (e) {
                is HttpException -> fromExceptionToHttpError(e = e, errorBody = errorBody)
                is SocketTimeoutException -> NetworkError.TimeOutError()
                is IOException -> NetworkError.ConnectionError()
                else -> NetworkError.UndefinedError()
            }
        )
    }

    private fun fromExceptionToHttpError(e: HttpException, errorBody: String?): NetworkError {
        return when {
            isBusinessErrorCode(errorCode = e.code()) -> {
                NetworkError.BusinessError(
                    httpErrorCode = e.code(),
                    errorMessage = errorBody,
                    cause = e.cause
                )
            }

            isServerError(errorCode = e.code()) -> {
                NetworkError.ServerError(
                    httpErrorCode = e.code(),
                    errorMessage = errorBody,
                    cause = e.cause
                )
            }

            else -> NetworkError.UndefinedError()
        }
    }

    private fun isBusinessErrorCode(errorCode: Int) = errorCode in 400..499
    private fun isServerError(errorCode: Int) = errorCode in 500..599
}