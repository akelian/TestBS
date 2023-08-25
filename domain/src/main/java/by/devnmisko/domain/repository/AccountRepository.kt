package by.devnmisko.domain.repository

import by.devnmisko.domain.model.Output
import by.devnmisko.domain.model.SignUserDomainRequestModel
import by.devnmisko.domain.model.SignUserDomainResponseModel
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun signIn(input: SignUserDomainRequestModel) : Flow<Output<SignUserDomainResponseModel>>
    fun signUp(input: SignUserDomainRequestModel) : Flow<Output<SignUserDomainResponseModel>>
}