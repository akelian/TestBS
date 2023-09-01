package by.devnmisko.domain.usecase.images

import by.devnmisko.domain.model.ImageDomainResponseModel
import kotlinx.coroutines.flow.Flow

interface GetImagesFromDBUseCase {
    operator fun  invoke() : Flow<List<ImageDomainResponseModel>>
}