package by.devnmisko.testbs.ui.loginscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.devnmisko.domain.model.Output
import by.devnmisko.domain.model.SignUserDomainResponseModel
import by.devnmisko.domain.usecase.sharedpref.SaveUserUseCase
import by.devnmisko.domain.usecase.signin.SignInUseCase
import by.devnmisko.domain.usecase.signup.SignUpUseCase
import by.devnmisko.testbs.model.SignUserAppRequestModel
import by.devnmisko.testbs.model.toDomainModel
import by.devnmisko.testbs.utils.Const.Pattern.USERNAME_PATTERN
import by.devnmisko.testbs.utils.Const.Range.PASSWORD_RANGE
import by.devnmisko.testbs.utils.Const.Range.USERNAME_RANGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

class StartUpFragmentViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val saveUserUseCase: SaveUserUseCase,
) : ViewModel() {

    private val _loadingState = MutableStateFlow(false)
    internal val loadingState: StateFlow<Boolean> = _loadingState

    private val _loginState = MutableStateFlow<Output<SignUserDomainResponseModel>?>(null)
    internal val loginState: StateFlow<Output<SignUserDomainResponseModel>?> = _loginState

    private val _sigUpState = MutableStateFlow<Output<SignUserDomainResponseModel>?>(null)
    internal val signUpState: StateFlow<Output<SignUserDomainResponseModel>?> = _sigUpState

    fun login(input: SignUserAppRequestModel) = viewModelScope.launch {
        signInUseCase(input.toDomainModel()).onEach {
            _loginState.value = it
            when (it.status) {
                    Output.Status.SUCCESS-> {
                        saveCredentials(it.data)
                        _loadingState.value = false
                    }

                Output.Status.ERROR -> _loadingState.value = false
                Output.Status.LOADING -> _loadingState.value = true
            }
        }.collect()
    }

    fun signUp(input: SignUserAppRequestModel) = viewModelScope.launch {
        signUpUseCase(input.toDomainModel()).onEach {
            _sigUpState.value = it
            _loadingState.value = it.status == Output.Status.LOADING
        }.collect()
    }

    private fun saveCredentials(data: SignUserDomainResponseModel?) {
        data?.let {
            saveUserUseCase(it)
        }
    }

    fun validate(username: String, password: String, confirmPassword: String? = null): Boolean {
        val isUsernameValid = validateUsername(username)
        val isPasswordValid = password.length in PASSWORD_RANGE
        val isConfirmPasswordValid =
            if (confirmPassword != null) password == confirmPassword else true
        return isUsernameValid && isPasswordValid && isConfirmPasswordValid
    }

    private fun validateUsername(username: String): Boolean {
        val usernamePattern = Pattern.compile(USERNAME_PATTERN)
        val usernameMatcher = usernamePattern.matcher(username)
        return username.length in USERNAME_RANGE && usernameMatcher.matches()
    }
}