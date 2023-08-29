package by.devnmisko.domain.di

import by.devnmisko.domain.usecase.images.GetImagesUseCase
import by.devnmisko.domain.usecase.images.GetImagesUseCaseImpl
import by.devnmisko.domain.usecase.images.PostImageUseCase
import by.devnmisko.domain.usecase.images.PostImageUseCaseImpl
import by.devnmisko.domain.usecase.sharedpref.GetTokenUseCase
import by.devnmisko.domain.usecase.sharedpref.GetTokenUseCaseImpl
import by.devnmisko.domain.usecase.sharedpref.GetUsernameUseCase
import by.devnmisko.domain.usecase.sharedpref.GetUsernameUseCaseImpl
import by.devnmisko.domain.usecase.sharedpref.SaveUserUseCase
import by.devnmisko.domain.usecase.sharedpref.SaveUserUseCaseImpl
import by.devnmisko.domain.usecase.signin.SignInUseCase
import by.devnmisko.domain.usecase.signin.SignInUseCaseImpl
import by.devnmisko.domain.usecase.signup.SignUpUseCase
import by.devnmisko.domain.usecase.signup.SignUpUseCaseImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class UseCaseModule {
    @Binds
    @Singleton
    internal abstract fun bindSignInUseCase(useCaseImpl: SignInUseCaseImpl): SignInUseCase
    @Binds
    @Singleton
    internal abstract fun bindSignUpUseCase(useCaseImpl: SignUpUseCaseImpl): SignUpUseCase
    @Binds
    @Singleton
    internal abstract fun bindSaveUser(useCaseImpl: SaveUserUseCaseImpl): SaveUserUseCase
    @Binds
    @Singleton
    internal abstract fun bindGetTokenUseCase(useCaseImpl: GetTokenUseCaseImpl): GetTokenUseCase
    @Binds
    @Singleton
    internal abstract fun bindGetUserNameUseCase(useCaseImpl: GetUsernameUseCaseImpl): GetUsernameUseCase
    @Binds
    @Singleton
    internal abstract fun bindGetListUseCase(useCaseImpl: GetImagesUseCaseImpl): GetImagesUseCase
    @Binds
    @Singleton
    internal abstract fun bindPostImageUseCase(useCaseImpl: PostImageUseCaseImpl): PostImageUseCase
}