package by.devnmisko.data.source.remote.datasource

import by.devnmisko.data.source.local.datasource.SharedPreferencesSource
import by.devnmisko.data.source.remote.api.ImagesApi
import by.devnmisko.data.source.remote.model.BaseResponseModel
import by.devnmisko.data.source.remote.model.ImageApiRequestModel
import by.devnmisko.data.source.remote.model.ImageApiResponseModel
import by.devnmisko.domain.model.Output
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class ImagesRemoteDataSource @Inject constructor(
    @Named("Retrofit") retrofit: Retrofit,
    private val imagesApi: ImagesApi,
    private val sharedPreferencesSource: SharedPreferencesSource
) : BaseRemoteDataSource(retrofit) {
    suspend fun getImages(page: Int): Output<BaseResponseModel<List<ImageApiResponseModel>>> {
        return getResponse(
            request = { imagesApi.getImages(sharedPreferencesSource.getToken(), page) },
            defaultErrorMessage = "Something went wrong"
        )
    }

    suspend fun postImage(image: ImageApiRequestModel): Output<BaseResponseModel<ImageApiResponseModel>> {
        return getResponse(
            request = { imagesApi.postImage(sharedPreferencesSource.getToken(), image) },
            defaultErrorMessage = "Something went wrong"
        )
    }

    suspend fun removeImage(id: Int): Output<BaseResponseModel<Void>> {
        return getResponse(
            request = { imagesApi.removeImage(sharedPreferencesSource.getToken(), id) },
            defaultErrorMessage = "Something went wrong"
        )
    }


}