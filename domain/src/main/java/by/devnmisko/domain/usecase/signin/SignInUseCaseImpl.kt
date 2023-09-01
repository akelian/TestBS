package by.devnmisko.domain.usecase.signin

import by.devnmisko.domain.model.Output
import by.devnmisko.domain.model.SignUserDomainRequestModel
import by.devnmisko.domain.model.SignUserDomainResponseModel
import by.devnmisko.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SignInUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository
) : SignInUseCase {
    override fun invoke(input: SignUserDomainRequestModel): Flow<Output<SignUserDomainResponseModel>> = accountRepository.signIn(input)
}