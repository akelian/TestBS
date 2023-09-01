package by.devnmisko.domain.usecase.sharedpref

import by.devnmisko.domain.model.SignUserDomainResponseModel

interface SaveUserUseCase {
    operator fun invoke(user: SignUserDomainResponseModel)
}