package by.devnmisko.data.repository

import by.devnmisko.data.repository.base.BaseRepository
import by.devnmisko.data.source.remote.datasource.ImagesRemoteDataSource
import by.devnmisko.data.source.remote.model.ImageApiRequestModel
import by.devnmisko.data.source.remote.model.toDomainModel
import by.devnmisko.domain.model.ImageDomainRequestModel
import by.devnmisko.domain.model.ImageDomainResponseModel
import by.devnmisko.domain.model.Output
import by.devnmisko.domain.repository.ImagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val imagesRemoteDataSource: ImagesRemoteDataSource
) : ImagesRepository, BaseRepository() {

    override fun getImages(page: Int): Flow<Output<List<ImageDomainResponseModel>>> {
        return flow {
            emit(Output.loading())
            val response = imagesRemoteDataSource.getImages(page)
            emit(
                Output(
                    response.status,
                    response.data?.data?.map { it.toDomainModel() },
                    response.error,
                    response.message
                )
            )
        }.flowOn(Dispatchers.IO)
    }

    override fun postImage(image: ImageDomainRequestModel): Flow<Output<ImageDomainResponseModel>> {
        return flow {
            emit(Output.loading())
            val response = imagesRemoteDataSource.postImage(ImageApiRequestModel.toDataModel(image))
            emit(
                Output(
                    response.status,
                    response.data?.data?.toDomainModel() ,
                    response.error,
                    response.message
                )
            )
        }.flowOn(Dispatchers.IO)
    }


}