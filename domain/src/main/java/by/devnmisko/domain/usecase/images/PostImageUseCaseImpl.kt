package by.devnmisko.domain.usecase.images

import by.devnmisko.domain.model.ImageDomainRequestModel
import by.devnmisko.domain.model.ImageDomainResponseModel
import by.devnmisko.domain.model.Output
import by.devnmisko.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostImageUseCaseImpl @Inject constructor(
    private val imagesRepository: ImagesRepository
) : PostImageUseCase{
    override fun invoke(image: ImageDomainRequestModel): Flow<Output<ImageDomainResponseModel>> {
       return imagesRepository.postImage(image)
    }

}