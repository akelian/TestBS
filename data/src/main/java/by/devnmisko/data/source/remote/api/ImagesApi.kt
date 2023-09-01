package by.devnmisko.data.source.remote.api

import by.devnmisko.data.source.remote.model.BaseResponseModel
import by.devnmisko.data.source.remote.model.ImageApiRequestModel
import by.devnmisko.data.source.remote.model.ImageApiResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ImagesApi {
    @GET("/api/image")
    suspend fun getImages(@Header("Access-Token") token: String, @Query("page") page: Int): Response<BaseResponseModel<List<ImageApiResponseModel>>>

    @POST("/api/image")
    suspend fun postImage(@Header("Access-Token") token: String, @Body image: ImageApiRequestModel): Response<BaseResponseModel<ImageApiResponseModel>>

    @DELETE("/api/image/{id}")
    suspend fun removeImage(@Header("Access-Token") token: String, @Path("id") id: Int) : Response<BaseResponseModel<Void>>

}