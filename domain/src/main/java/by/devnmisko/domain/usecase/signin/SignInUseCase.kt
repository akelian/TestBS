package by.devnmisko.domain.usecase.signin

import by.devnmisko.domain.model.Output
import by.devnmisko.domain.model.SignUserDomainRequestModel
import by.devnmisko.domain.model.SignUserDomainResponseModel
import kotlinx.coroutines.flow.Flow

interface SignInUseCase {
    operator fun invoke(input: SignUserDomainRequestModel): Flow<Output<SignUserDomainResponseModel>>
}