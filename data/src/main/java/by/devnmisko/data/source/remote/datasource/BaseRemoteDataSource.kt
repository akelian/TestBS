package by.devnmisko.data.source.remote.datasource

import by.devnmisko.domain.model.ApiError
import by.devnmisko.domain.model.Output
import by.devnmisko.domain.model.buildErrorMessage
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

abstract class BaseRemoteDataSource constructor(
    private val retrofit: Retrofit
) {

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    suspend fun <T> getResponse(
        request: suspend () -> Response<T>,
        defaultErrorMessage: String
    ): Output<T> {
        return try {
            println("I'm working in thread ${Thread.currentThread().name}")
            val result = request.invoke()
            if (result.isSuccessful) {
                return Output.success(result.body())
            } else {
                val errorResponse = parseError(result)
                errorResponse?.buildErrorMessage()
                Output.error(errorResponse?.errorMessage ?: defaultErrorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            Output.error("Unknown Error", null)
        }
    }

    @Suppress("SwallowedException")
    private fun parseError(response: Response<*>): ApiError? {
        val converter =
            retrofit.responseBodyConverter<ApiError>(ApiError::class.java, arrayOfNulls(0))
        return try {
            response.errorBody()?.let {
                converter.convert(it)
            }
        } catch (e: IOException) {
            ApiError()
        }
    }
}
