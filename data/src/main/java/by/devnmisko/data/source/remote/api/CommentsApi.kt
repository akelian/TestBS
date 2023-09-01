package by.devnmisko.data.source.remote.api

import by.devnmisko.data.source.remote.model.BaseResponseModel
import by.devnmisko.data.source.remote.model.CommentApiRequestModel
import by.devnmisko.data.source.remote.model.CommentApiResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentsApi {
    @GET("/api/image/{imageId}/comment")
    suspend fun getComments(
        @Header("Access-Token") token: String,
        @Path("imageId") imageId: Int,
        @Query("page") page: Int
    ): Response<BaseResponseModel<List<CommentApiResponseModel>>>

    @POST("/api/image/{imageId}/comment")
    suspend fun postComment(
        @Header("Access-Token") token: String,
        @Path("imageId") imageId: Int,
        @Body comment: CommentApiRequestModel
    ): Response<BaseResponseModel<CommentApiResponseModel>>

    @DELETE("/api/image/{imageId}/comment/{commentId}")
    suspend fun removeComment(
        @Header("Access-Token") token: String,
        @Path("imageId") imageId: Int,
        @Path("commentId") commentId: Int
    ): Response<BaseResponseModel<Void>>

}