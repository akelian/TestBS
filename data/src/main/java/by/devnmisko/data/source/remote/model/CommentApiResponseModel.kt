package by.devnmisko.data.source.remote.model

import by.devnmisko.domain.model.CommentDomainResponseModel
import com.google.gson.annotations.SerializedName

data class CommentApiResponseModel(
    @SerializedName("id") val id: Int,
    @SerializedName("date") val date: Long,
    @SerializedName("text") val text: String
)

fun CommentApiResponseModel.toDomainModel(): CommentDomainResponseModel {
    return CommentDomainResponseModel(
        id = id,
        date = date * 1000,
        text = text
    )
}


