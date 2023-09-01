package by.devnmisko.domain.usecase.images

import by.devnmisko.domain.model.ImageDomainResponseModel
import by.devnmisko.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesFromDBUseCaseImpl @Inject constructor(
    private val imagesRepository: ImagesRepository
) : GetImagesFromDBUseCase {
    override fun invoke(): Flow<List<ImageDomainResponseModel>> {
        return imagesRepository.allImages
    }
}