package by.devnmisko.domain.usecase.images

import androidx.paging.PagingData
import by.devnmisko.domain.model.ImageDomainResponseModel
import by.devnmisko.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesUseCaseImpl @Inject constructor(
    private val imagesRepository: ImagesRepository
) : GetImagesUseCase {
    override fun invoke(): Flow<PagingData<ImageDomainResponseModel>> =
        imagesRepository.getImages()

}