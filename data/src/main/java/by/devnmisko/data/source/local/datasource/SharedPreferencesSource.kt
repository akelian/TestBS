package by.devnmisko.data.source.local.datasource

import android.content.Context
import android.content.SharedPreferences
import by.devnmisko.data.AppContracts.PREFS
import by.devnmisko.data.AppContracts.TOKEN
import by.devnmisko.data.AppContracts.USERNAME
import by.devnmisko.domain.model.SignUserDomainResponseModel

class SharedPreferencesSource(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

     fun saveUser(user: SignUserDomainResponseModel) {
        setString(TOKEN, user.token)
        setString(USERNAME, user.login)
    }

     fun getToken() : String {
        return sharedPreferences.getString(TOKEN, null) ?: ""
    }
     fun getUsername() : String {
        return sharedPreferences.getString(USERNAME, null) ?:  "Username"
    }

    private fun setString(key: String, value: String) {
        sharedPreferences.edit()
            .putString(key, value)
            .apply()
    }
}