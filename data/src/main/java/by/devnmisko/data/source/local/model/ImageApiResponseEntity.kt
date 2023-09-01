package by.devnmisko.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.devnmisko.domain.model.ImageDomainResponseModel

@Entity
data class ImageApiEntity(
    @PrimaryKey(autoGenerate = true)
    val tableId: Int? = null,
    val id: Int,
    val url: String,
    val date: Long,
    val lat: Double,
    val lng: Double
)

fun ImageApiEntity.toDomainModel(): ImageDomainResponseModel {
    return ImageDomainResponseModel(
        id = id,
        url = url,
        date = date,
        lat = lat,
        lng = lng
    )
}