package by.devnmisko.domain.usecase.comments

import by.devnmisko.domain.model.Output
import by.devnmisko.domain.repository.CommentsRepository
import by.devnmisko.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveCommentUseCaseImpl @Inject constructor(
    private val commentsRepository: CommentsRepository
) : RemoveCommentUseCase {
    override suspend fun invoke(imageId: Int, commentId: Int): Flow<Output<Void>> {
       return commentsRepository.removeComment(imageId, commentId)
    }

}