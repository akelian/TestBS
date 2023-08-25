package by.devnmisko.domain.usecase.sharedpref

import by.devnmisko.domain.repository.SharedPrefRepository
import javax.inject.Inject

class GetTokenUseCaseImpl @Inject constructor(
    private val sharedPrefRepository: SharedPrefRepository
) : GetTokenUseCase {
    override fun invoke(): String {
        return sharedPrefRepository.getToken()
    }
}