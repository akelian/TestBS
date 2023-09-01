package by.devnmisko.domain.repository

import androidx.paging.PagingData
import by.devnmisko.domain.model.ImageDomainRequestModel
import by.devnmisko.domain.model.ImageDomainResponseModel
import by.devnmisko.domain.model.Output
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    val allImages: Flow<List<ImageDomainResponseModel>>
    fun getImages(): Flow<PagingData<ImageDomainResponseModel>>
    suspend fun postImage(image: ImageDomainRequestModel) : Flow<Output<ImageDomainResponseModel>>
    suspend fun removeImage(id: Int) : Flow<Output<Void>>
}