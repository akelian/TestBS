package by.devnmisko.domain.repository

import by.devnmisko.domain.model.SignUserDomainResponseModel
import dagger.Provides

interface SharedPrefRepository {

    fun saveUser(user : SignUserDomainResponseModel)
    fun getToken() : String
    fun getUserName(): String
}