package com.example.countries_challenge.data.util.net

import android.util.Log
import com.example.countries_challenge.data.util.net.mapper.ErrorMapper
import com.example.countries_challenge.domain.utils.UseCaseResult
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T> safeApiCall(call: suspend () -> T): UseCaseResult<T> {
        return try {
            UseCaseResult.Success(call())
        } catch (e: Exception) {
            Log.e("Network Error", "${e.message}")
            var errorBody: String? = null
            (e as? HttpException)?.let {
                errorBody = e.getErrorBody()
            }
            return ErrorMapper.fromExceptionToUseCaseResultError(e, errorBody)
        }
    }

    private companion object {
        private fun HttpException.getErrorBody(): String {
            return this.response()?.errorBody()?.runCatching {
                bytes().decodeToString()
            }?.getOrNull().orEmpty()
        }
    }
}