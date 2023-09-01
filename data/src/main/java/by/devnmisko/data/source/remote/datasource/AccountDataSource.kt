package by.devnmisko.data.source.remote.datasource

import by.devnmisko.data.source.remote.api.SignInApi
import by.devnmisko.data.source.remote.api.SignUpApi
import by.devnmisko.data.source.remote.model.BaseResponseModel
import by.devnmisko.data.source.remote.model.SignUserApiRequestModel
import by.devnmisko.data.source.remote.model.SignUserApiResponseModel
import by.devnmisko.domain.model.Output
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class AccountDataSource @Inject constructor(
    @Named("Retrofit") retrofit: Retrofit,
    private val signUpApi: SignUpApi,
    private val signInApi: SignInApi
) : BaseRemoteDataSource(retrofit) {
    
     suspend fun signIn(input: SignUserApiRequestModel): Output<BaseResponseModel<SignUserApiResponseModel>> {
        return getResponse(
            request = { signInApi.signIn(input) },
            defaultErrorMessage = "Something went wrong"
        )
    }    
    
     suspend fun signUp(input: SignUserApiRequestModel): Output<BaseResponseModel<SignUserApiResponseModel>> {
        return getResponse(
            request = { signUpApi.signUp(input) },
            defaultErrorMessage = "Something went wrong"
        )
    }
    
}