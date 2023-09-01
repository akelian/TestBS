package by.devnmisko.domain.model


data class ImageDomainRequestModel (
    val base64Image: String,
    val date: Long,
    val lat: Double,
    val lng: Double
)