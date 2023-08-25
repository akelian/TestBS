package by.devnmisko.data.repository

import by.devnmisko.data.repository.base.BaseRepository
import by.devnmisko.data.source.local.datasource.SharedPreferencesSource
import by.devnmisko.domain.model.SignUserDomainResponseModel
import by.devnmisko.domain.repository.SharedPrefRepository
import javax.inject.Inject

class SharedPrefRepositoryImpl @Inject constructor(
    private val sharedPreferencesSource: SharedPreferencesSource
) : SharedPrefRepository, BaseRepository() {
    override fun saveUser(user: SignUserDomainResponseModel) {
        sharedPreferencesSource.saveUser(user)
    }

    override fun getToken(): String {
        return sharedPreferencesSource.getToken()
    }

    override fun getUserName(): String {
        return sharedPreferencesSource.getUsername()
    }

}