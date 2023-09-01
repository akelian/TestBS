package by.devnmisko.domain.usecase.comments

import androidx.paging.PagingData
import by.devnmisko.domain.model.CommentDomainResponseModel
import kotlinx.coroutines.flow.Flow

interface GetCommentsUseCase {
    operator fun invoke(imageId: Int): Flow<PagingData<CommentDomainResponseModel>>
}