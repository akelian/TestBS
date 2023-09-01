package by.devnmisko.testbs.ui.mainscreen

import androidx.lifecycle.ViewModel
import by.devnmisko.domain.usecase.sharedpref.GetUsernameUseCase
import javax.inject.Inject

class MenuFragmentViewModel @Inject constructor(
    private val getUsernameUseCase: GetUsernameUseCase
): ViewModel() {

    fun getUsername () =  getUsernameUseCase()
}