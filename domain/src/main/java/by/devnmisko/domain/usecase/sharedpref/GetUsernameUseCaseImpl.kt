package by.devnmisko.domain.usecase.sharedpref

import by.devnmisko.domain.repository.SharedPrefRepository
import javax.inject.Inject

class GetUsernameUseCaseImpl @Inject constructor(
    private val sharedPrefRepository: SharedPrefRepository
) : GetUsernameUseCase {
    override fun invoke(): String {
       return sharedPrefRepository.getUserName()
    }
}