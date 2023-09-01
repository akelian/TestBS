package by.devnmisko.data.source.remote.model

import com.google.gson.annotations.SerializedName
import retrofit2.Response

data class BaseResponseModel<T>(
    @SerializedName("status")
    val status: String,

    @SerializedName("data")
    val data: T
)