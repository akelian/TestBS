package by.devnmisko.domain.usecase.signup

import by.devnmisko.domain.model.Output
import by.devnmisko.domain.model.SignUserDomainRequestModel
import by.devnmisko.domain.model.SignUserDomainResponseModel
import by.devnmisko.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SignUpUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository
) : SignUpUseCase {
    override fun invoke(input: SignUserDomainRequestModel): Flow<Output<SignUserDomainResponseModel>> = accountRepository.signUp(input)
}