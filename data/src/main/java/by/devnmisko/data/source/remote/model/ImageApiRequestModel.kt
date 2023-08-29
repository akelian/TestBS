package by.devnmisko.data.source.remote.model

import by.devnmisko.domain.model.ImageDomainRequestModel
import com.google.gson.annotations.SerializedName

data class ImageApiRequestModel(
    @SerializedName("base64Image")
    val base64Image: String,
    @SerializedName("date")
    val date: Long,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
) {
    companion object {
        fun toDataModel(image: ImageDomainRequestModel): ImageApiRequestModel {
            return ImageApiRequestModel(
                base64Image = image.base64Image,
                date = image.date,
                lat = image.lat,
                lng = image.lng
            )
        }
    }
}