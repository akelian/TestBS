package by.devnmisko.testbs.model

import by.devnmisko.domain.model.SignUserDomainRequestModel

data class SignUserAppRequestModel(
    val login: String,

    val password: String
)
fun SignUserAppRequestModel.toDomainModel()= SignUserDomainRequestModel(
    login = login,
    password = password,
)