package by.devnmisko.data.repository

import by.devnmisko.data.repository.base.BaseRepository
import by.devnmisko.data.source.remote.datasource.AccountDataSource
import by.devnmisko.data.source.remote.model.SignUserApiRequestModel
import by.devnmisko.data.source.remote.model.toDomainModel
import by.devnmisko.domain.model.Output
import by.devnmisko.domain.model.SignUserDomainRequestModel
import by.devnmisko.domain.model.SignUserDomainResponseModel
import by.devnmisko.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountDataSource: AccountDataSource
) : AccountRepository, BaseRepository() {

    override fun signIn(input: SignUserDomainRequestModel) : Flow<Output<SignUserDomainResponseModel>> {
        return flow {
            emit(Output.loading())
            val response = accountDataSource.signIn(SignUserApiRequestModel.toDataModel(input))
            emit(
                Output(
                    response.status,
                    response.data?.data?.toDomainModel(),
                    response.error,
                    response.message
                )
            )
        }.flowOn(Dispatchers.IO)
    }

    override fun signUp(input: SignUserDomainRequestModel): Flow<Output<SignUserDomainResponseModel>> {
        return flow {
            emit(Output.loading())
            val response = accountDataSource.signUp(SignUserApiRequestModel.toDataModel(input))
            emit(
                Output(
                    response.status,
                    response.data?.data?.toDomainModel(),
                    response.error,
                    response.message
                )
            )
        }.flowOn(Dispatchers.IO)
    }

}