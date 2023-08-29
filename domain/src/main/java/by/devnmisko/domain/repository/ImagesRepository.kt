package by.devnmisko.domain.repository

import by.devnmisko.domain.model.ImageDomainRequestModel
import by.devnmisko.domain.model.ImageDomainResponseModel
import by.devnmisko.domain.model.Output
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    fun getImages(page: Int): Flow<Output<List<ImageDomainResponseModel>>>
    fun postImage(image: ImageDomainRequestModel) : Flow<Output<ImageDomainResponseModel>>
}