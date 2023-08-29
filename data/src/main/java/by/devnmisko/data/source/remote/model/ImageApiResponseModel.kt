package by.devnmisko.data.source.remote.model

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
        date = date,
        lat = lat,
        lng = lng
    )
}
