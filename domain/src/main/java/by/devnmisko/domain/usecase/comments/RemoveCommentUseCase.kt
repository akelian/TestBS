package by.devnmisko.domain.usecase.comments

import by.devnmisko.domain.model.Output
import kotlinx.coroutines.flow.Flow

interface RemoveCommentUseCase {
    suspend operator fun invoke(imageId:Int, commentId: Int) : Flow<Output<Void>>
}