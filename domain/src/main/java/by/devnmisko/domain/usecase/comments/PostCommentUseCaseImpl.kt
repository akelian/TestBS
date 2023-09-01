package by.devnmisko.domain.usecase.comments

import by.devnmisko.domain.model.CommentDomainRequestModel
import by.devnmisko.domain.model.CommentDomainResponseModel
import by.devnmisko.domain.model.Output
import by.devnmisko.domain.repository.CommentsRepository
import by.devnmisko.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostCommentUseCaseImpl @Inject constructor(
    private val commentsRepository: CommentsRepository
) : PostCommentUseCase {
    override suspend fun invoke(
        imageId: Int,
        comment: CommentDomainRequestModel
    ): Flow<Output<CommentDomainResponseModel>> {
        return commentsRepository.postComment(imageId, comment)
    }

}