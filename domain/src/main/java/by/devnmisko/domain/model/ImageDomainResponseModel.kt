package by.devnmisko.domain.model

data class ImageDomainResponseModel(
    val id: Int,
    val url: String,
    val date: Long,
    val lat: Double,
    val lng: Double
)