package by.devnmisko.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import by.devnmisko.data.source.local.ImagesDao
import by.devnmisko.data.source.local.ImagesDataBase
import by.devnmisko.data.source.local.RemoteKeyDao
import by.devnmisko.data.source.local.model.ImageApiEntity
import by.devnmisko.data.source.local.model.RemoteKey
import by.devnmisko.data.source.remote.datasource.ImagesRemoteDataSource
import by.devnmisko.data.source.remote.model.toEntity
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ImagesRemoteMediator @Inject constructor(
    private val imagesRemoteDataSource: ImagesRemoteDataSource,
    private val imagesDao: ImagesDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val database: ImagesDataBase
) : RemoteMediator<Int, ImageApiEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageApiEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = imagesRemoteDataSource.getImages(page = currentPage)
            val endOfPaginationReached = response.data?.data?.isEmpty()

            val prevPage = if (currentPage == 0) null else currentPage - 1
            val nextPage = if (endOfPaginationReached == true) null else currentPage + 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    imagesDao.deleteAll()
                    remoteKeyDao.deleteAllRemoteKeys()
                }
                val keys = response.data?.data?.map { images ->
                    RemoteKey(
                        id = images.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                keys?.let { remoteKeyDao.addAllRemoteKeys(remoteKeys = it) }
                response.data?.data?.map { image ->  image.toEntity()}?.let { images -> imagesDao.addAll(images) }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached == true)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, ImageApiEntity>
    ): RemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { image ->
                remoteKeyDao.getRemoteKeys(id = image.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, ImageApiEntity>
    ): RemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { image ->
                remoteKeyDao.getRemoteKeys(id = image.id)
            }
    }

}