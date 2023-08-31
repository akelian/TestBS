package by.devnmisko.domain.usecase.images

import by.devnmisko.domain.model.Output
import by.devnmisko.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveImageUseCaseImpl @Inject constructor(
    private val imagesRepository : ImagesRepository
) : RemoveImageUseCase {
    override suspend fun invoke(id: Int): Flow<Output<Void>> {
       return imagesRepository.removeImage(id)
    }
}