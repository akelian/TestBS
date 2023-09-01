package by.devnmisko.domain.repository

import androidx.paging.PagingData
import by.devnmisko.domain.model.CommentDomainRequestModel
import by.devnmisko.domain.model.CommentDomainResponseModel
import by.devnmisko.domain.model.Output
import kotlinx.coroutines.flow.Flow

interface CommentsRepository {
    fun getComments(imageId: Int): Flow<PagingData<CommentDomainResponseModel>>
    suspend fun postComment(imageId: Int, comment: CommentDomainRequestModel) : Flow<Output<CommentDomainResponseModel>>
    suspend fun removeComment(imageId: Int, commentId: Int) : Flow<Output<Void>>
}