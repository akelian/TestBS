package by.devnmisko.domain.usecase.images

import androidx.paging.PagingData
import by.devnmisko.domain.model.ImageDomainResponseModel
import kotlinx.coroutines.flow.Flow

interface GetImagesUseCase {
    operator fun invoke(): Flow<PagingData<ImageDomainResponseModel>>
}