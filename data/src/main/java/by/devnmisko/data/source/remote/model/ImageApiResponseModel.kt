package by.devnmisko.data.source.remote.model

import by.devnmisko.data.source.local.model.ImageApiEntity
import by.devnmisko.domain.model.ImageDomainResponseModel
import com.google.gson.annotations.SerializedName

data class ImageApiResponseModel(
    @SerializedName("id") val id: Int,
    @SerializedName("url") val url: String,
    @SerializedName("date") val date: Long,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
)

fun ImageApiResponseModel.toDomainModel(): ImageDomainResponseModel {
    return ImageDomainResponseModel(
        id = id,
        url = url,
        date = date * 1000,
        lat = lat,
        lng = lng
    )
}

fun ImageApiResponseModel.toEntity(): ImageApiEntity {
    return ImageApiEntity(
        id = id,
        url = url,
        date = date * 1000,
        lat = lat,
        lng = lng
    )
}
