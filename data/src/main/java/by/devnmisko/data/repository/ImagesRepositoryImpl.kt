package by.devnmisko.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSourceFactory
import androidx.paging.map
import by.devnmisko.data.paging.ImagesRemoteMediator
import by.devnmisko.data.repository.base.BaseRepository
import by.devnmisko.data.source.local.ImagesDao
import by.devnmisko.data.source.local.model.toDomainModel
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
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val imagesRemoteDataSource: ImagesRemoteDataSource,
    private val imagesDao: ImagesDao,
    private val imagesRemoteMediator: ImagesRemoteMediator,
) : ImagesRepository, BaseRepository() {

    @OptIn(ExperimentalPagingApi::class)
    override fun getImages(): Flow<PagingData<ImageDomainResponseModel>> {
        val paging = Pager(
            config = PagingConfig(pageSize = 10, initialLoadSize = 18, enablePlaceholders = true),
            remoteMediator = imagesRemoteMediator,
            pagingSourceFactory = PagingSourceFactory { imagesDao.pagingSource() }
        ).flow

        return paging.map { pagingData ->
            pagingData.map { image ->
                image.toDomainModel()
            }
        }
    }

    override suspend fun postImage(image: ImageDomainRequestModel): Flow<Output<ImageDomainResponseModel>> {
        return flow {
            emit(Output.loading())
            val response = imagesRemoteDataSource.postImage(ImageApiRequestModel.toDataModel(image))
            emit(
                Output(
                    response.status,
                    response.data?.data?.toDomainModel(),
                    response.error,
                    response.message
                )
            )
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun removeImage(id: Int) : Flow<Output<Void>> {
        return flow {
            emit(Output.loading())
            val response = imagesRemoteDataSource.removeImage(id)
            emit(
                Output(
                    response.status,
                    null,
                    response.error,
                    response.message
                )
            )
        }
    }
}