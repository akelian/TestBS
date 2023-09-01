package by.devnmisko.data.source.remote.datasource

import by.devnmisko.data.source.local.datasource.SharedPreferencesSource
import by.devnmisko.data.source.remote.api.CommentsApi
import by.devnmisko.data.source.remote.model.BaseResponseModel
import by.devnmisko.data.source.remote.model.CommentApiRequestModel
import by.devnmisko.data.source.remote.model.CommentApiResponseModel
import by.devnmisko.domain.model.Output
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class CommentsRemoteDataSource @Inject constructor(
    @Named("Retrofit") retrofit: Retrofit,
    private val commentsApi: CommentsApi,
    private val sharedPreferencesSource: SharedPreferencesSource
) : BaseRemoteDataSource(retrofit) {
    suspend fun getComments(
        imageId: Int,
        page: Int
    ): Output<BaseResponseModel<List<CommentApiResponseModel>>> {
        return getResponse(
            request = {
                commentsApi.getComments(
                    sharedPreferencesSource.getToken(),
                    imageId = imageId,
                    page = page
                )
            },
            defaultErrorMessage = "Something went wrong"
        )
    }

    suspend fun postComment(
        imageId: Int,
        comment: CommentApiRequestModel
    ): Output<BaseResponseModel<CommentApiResponseModel>> {
        return getResponse(
            request = {
                commentsApi.postComment(
                    sharedPreferencesSource.getToken(),
                    imageId,
                    comment
                )
            },
            defaultErrorMessage = "Something went wrong"
        )
    }

    suspend fun removeComment(imageId: Int, commentId: Int): Output<BaseResponseModel<Void>> {
        return getResponse(
            request = {
                commentsApi.removeComment(
                    sharedPreferencesSource.getToken(),
                    imageId = imageId,
                    commentId = commentId
                )
            },
            defaultErrorMessage = "Something went wrong"
        )
    }


}