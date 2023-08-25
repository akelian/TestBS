package by.devnmisko.domain.model


data class SignUserDomainResponseModel(
    val userId: Int,

    val login: String,

    val token: String
)

