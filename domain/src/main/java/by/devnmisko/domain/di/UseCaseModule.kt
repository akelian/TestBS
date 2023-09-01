package by.devnmisko.domain.di

import by.devnmisko.domain.usecase.comments.GetCommentsUseCase
import by.devnmisko.domain.usecase.comments.GetCommentsUseCaseImpl
import by.devnmisko.domain.usecase.comments.PostCommentUseCase
import by.devnmisko.domain.usecase.comments.PostCommentUseCaseImpl
import by.devnmisko.domain.usecase.comments.RemoveCommentUseCase
import by.devnmisko.domain.usecase.comments.RemoveCommentUseCaseImpl
import by.devnmisko.domain.usecase.images.GetImagesFromDBUseCase
import by.devnmisko.domain.usecase.images.GetImagesFromDBUseCaseImpl
import by.devnmisko.domain.usecase.images.GetImagesUseCase
import by.devnmisko.domain.usecase.images.GetImagesUseCaseImpl
import by.devnmisko.domain.usecase.images.PostImageUseCase
import by.devnmisko.domain.usecase.images.PostImageUseCaseImpl
import by.devnmisko.domain.usecase.images.RemoveImageUseCase
import by.devnmisko.domain.usecase.images.RemoveImageUseCaseImpl
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
    internal abstract fun bindGetUserNameUseCase(useCaseImpl: GetUsernameUseCaseImpl): GetUsernameUseCase
    @Binds
    @Singleton
    internal abstract fun bindGetListUseCase(useCaseImpl: GetImagesUseCaseImpl): GetImagesUseCase
    @Binds
    @Singleton
    internal abstract fun bindPostImageUseCase(useCaseImpl: PostImageUseCaseImpl): PostImageUseCase
    @Binds
    @Singleton
    internal abstract fun bindRemoveImageUseCase(useCaseImpl: RemoveImageUseCaseImpl): RemoveImageUseCase
    @Binds
    @Singleton
    internal abstract fun bindGetImagesUseCase(useCaseImpl: GetImagesFromDBUseCaseImpl): GetImagesFromDBUseCase
    @Binds
    @Singleton
    internal abstract fun bindPostCommentUseCase(useCaseImpl: PostCommentUseCaseImpl): PostCommentUseCase
    @Binds
    @Singleton
    internal abstract fun bindRemoveCommentUseCase(useCaseImpl: RemoveCommentUseCaseImpl): RemoveCommentUseCase
    @Binds
    @Singleton
    internal abstract fun bindGetCommentsUseCase(useCaseImpl: GetCommentsUseCaseImpl): GetCommentsUseCase
}