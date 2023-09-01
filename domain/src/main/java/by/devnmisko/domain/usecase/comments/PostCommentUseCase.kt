package by.devnmisko.domain.usecase.comments

import by.devnmisko.domain.model.CommentDomainRequestModel
import by.devnmisko.domain.model.CommentDomainResponseModel
import by.devnmisko.domain.model.Output
import kotlinx.coroutines.flow.Flow

interface PostCommentUseCase {
    suspend operator fun invoke(imageId: Int, comment: CommentDomainRequestModel) : Flow<Output<CommentDomainResponseModel>>
}