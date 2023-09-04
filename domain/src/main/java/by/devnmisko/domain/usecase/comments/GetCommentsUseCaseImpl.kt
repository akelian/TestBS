package by.devnmisko.domain.usecase.comments

import androidx.paging.PagingData
import by.devnmisko.domain.model.CommentDomainResponseModel
import by.devnmisko.domain.repository.CommentsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCommentsUseCaseImpl @Inject constructor(
    private val commentsRepository: CommentsRepository
) : GetCommentsUseCase {
    override fun invoke(imageId: Int): Flow<PagingData<CommentDomainResponseModel>> {
        return commentsRepository.getComments(imageId)
    }

}