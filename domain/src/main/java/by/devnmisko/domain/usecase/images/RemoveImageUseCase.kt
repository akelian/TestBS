package by.devnmisko.domain.usecase.images

import by.devnmisko.domain.model.Output
import kotlinx.coroutines.flow.Flow

interface RemoveImageUseCase {
    suspend operator fun invoke(id: Int): Flow<Output<Void>>
}