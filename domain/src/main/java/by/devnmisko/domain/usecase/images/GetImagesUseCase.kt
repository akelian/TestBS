package by.devnmisko.domain.usecase.images

import by.devnmisko.domain.model.ImageDomainResponseModel
import by.devnmisko.domain.model.Output
import kotlinx.coroutines.flow.Flow

interface GetImagesUseCase {
    operator fun invoke(page: Int): Flow<Output<List<ImageDomainResponseModel>>>
}