package by.devnmisko.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import by.devnmisko.data.paging.CommentsPagingSource
import by.devnmisko.data.repository.base.BaseRepository
import by.devnmisko.data.source.remote.datasource.CommentsRemoteDataSource
import by.devnmisko.data.source.remote.model.CommentApiRequestModel
import by.devnmisko.data.source.remote.model.toDomainModel
import by.devnmisko.domain.model.CommentDomainRequestModel
import by.devnmisko.domain.model.CommentDomainResponseModel
import by.devnmisko.domain.model.Output
import by.devnmisko.domain.repository.CommentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CommentsRepositoryImpl @Inject constructor(
    private val commentsRemoteDataSource: CommentsRemoteDataSource,
    private val commentsPagingSource: CommentsPagingSource.Factory
) : CommentsRepository, BaseRepository() {

    override fun getComments(imageId: Int): Flow<PagingData<CommentDomainResponseModel>> {
        return Pager(
            config = PagingConfig(pageSize = 6)
        ) {
            commentsPagingSource.create(imageId)
        }.flow
    }

    override suspend fun postComment(
        imageId: Int,
        comment: CommentDomainRequestModel
    ): Flow<Output<CommentDomainResponseModel>> {
        return flow {
            emit(Output.loading())
            val response = commentsRemoteDataSource.postComment(
                imageId,
                CommentApiRequestModel.toDataModel(comment)
            )
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

    override suspend fun removeComment(imageId: Int, commentId: Int): Flow<Output<Void>> {
        return flow {
            emit(Output.loading())
            val response = commentsRemoteDataSource.removeComment(imageId, commentId)
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