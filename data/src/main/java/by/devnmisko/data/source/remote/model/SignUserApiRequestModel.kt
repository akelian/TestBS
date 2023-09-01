package by.devnmisko.data.source.remote.model

import by.devnmisko.domain.model.SignUserDomainRequestModel
import com.google.gson.annotations.SerializedName

data class SignUserApiRequestModel(
    @SerializedName("login")
    val login: String,

    @SerializedName("password")
    val password: String
) {
    companion object {
        fun toDataModel(input: SignUserDomainRequestModel) : SignUserApiRequestModel {
            return SignUserApiRequestModel(login = input.login, password = input.password)
        }
    }
}

