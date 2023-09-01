package by.devnmisko.data.source.remote.model

import by.devnmisko.domain.model.SignUserDomainResponseModel
import com.google.gson.annotations.SerializedName

data class SignUserApiResponseModel(
    @SerializedName("userId")
    val userId: Int,

    @SerializedName("login")
    val login: String,

    @SerializedName("token")
    val token: String
)

fun SignUserApiResponseModel.toDomainModel()= SignUserDomainResponseModel(
    userId = userId,
    login = login,
    token = token,
)