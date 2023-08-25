package by.devnmisko.data.source.remote.api

import by.devnmisko.data.source.remote.model.BaseResponseModel
import by.devnmisko.data.source.remote.model.SignUserApiRequestModel
import by.devnmisko.data.source.remote.model.SignUserApiResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpApi {
    @POST("/api/account/signup")
    suspend fun signUp(@Body input: SignUserApiRequestModel): Response<BaseResponseModel<SignUserApiResponseModel>>
}