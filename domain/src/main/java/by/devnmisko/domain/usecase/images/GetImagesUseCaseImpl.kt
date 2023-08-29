package by.devnmisko.domain.usecase.images

import by.devnmisko.domain.model.ImageDomainResponseModel
import by.devnmisko.domain.model.Output
import by.devnmisko.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesUseCaseImpl @Inject constructor(
    private val imagesRepository: ImagesRepository
) : GetImagesUseCase{
    override fun invoke(page: Int): Flow<Output<List<ImageDomainResponseModel>>> {
        return imagesRepository.getImages(page)
    }
}