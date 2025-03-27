package com.example.countries_challenge.domain.utils.networkerror

sealed class NetworkError(
    open var httpErrorCode: Int? = null,
    open val errorMessage: String? = null,
    @Transient override val cause: Throwable? = null,
) : Throwable(errorMessage, cause) {

    data class ConnectionError(
        override var httpErrorCode: Int? = null,
        override val errorMessage: String? = null,
        override val cause: Throwable? = null,
    ) : NetworkError(httpErrorCode, errorMessage, cause)

    data class TimeOutError(
        override var httpErrorCode: Int? = null,
        override val errorMessage: String? = null,
        override val cause: Throwable? = null,
    ) : NetworkError(httpErrorCode, errorMessage, cause)

    data class UndefinedError(
        override var httpErrorCode: Int? = null,
        override val errorMessage: String? = null,
        override val cause: Throwable? = null,
    ) : NetworkError(httpErrorCode, errorMessage, cause)

    data class ServerError(
        override var httpErrorCode: Int? = null,
        override val errorMessage: String? = null,
        override val cause: Throwable? = null,
    ) : NetworkError(httpErrorCode, errorMessage, cause)

    data class BusinessError(
        override var httpErrorCode: Int? = null,
        override val errorMessage: String? = null,
        override val cause: Throwable? = null,
    ) : NetworkError(httpErrorCode, errorMessage, cause)
}