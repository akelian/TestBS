package by.devnmisko.domain.usecase.sharedpref

import by.devnmisko.domain.model.SignUserDomainResponseModel
import by.devnmisko.domain.repository.SharedPrefRepository
import javax.inject.Inject

class SaveUserUseCaseImpl @Inject constructor(
    private val sharedPrefRepository: SharedPrefRepository
) : SaveUserUseCase{
    override fun invoke(user: SignUserDomainResponseModel) {
        sharedPrefRepository.saveUser(user)
    }

}