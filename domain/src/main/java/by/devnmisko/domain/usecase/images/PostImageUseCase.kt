package by.devnmisko.domain.usecase.images

import by.devnmisko.domain.model.ImageDomainRequestModel
import by.devnmisko.domain.model.ImageDomainResponseModel
import by.devnmisko.domain.model.Output
import kotlinx.coroutines.flow.Flow

interface PostImageUseCase {
    operator fun invoke(image: ImageDomainRequestModel) : Flow<Output<ImageDomainResponseModel>>
}