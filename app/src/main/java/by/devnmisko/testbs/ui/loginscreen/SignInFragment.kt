package by.devnmisko.testbs.ui.loginscreen


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import by.devnmisko.testbs.MainActivity
import by.devnmisko.testbs.R
import by.devnmisko.testbs.databinding.FragmentSigninBinding
import by.devnmisko.testbs.model.SignUserAppRequestModel
import by.devnmisko.testbs.ui.base.BaseFragment
import by.devnmisko.testbs.utils.setOnSafeClickListener
import javax.inject.Inject

class SignInFragment : BaseFragment<FragmentSigninBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: StartUpFragmentViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSigninBinding
        get() = FragmentSigninBinding::inflate

    override fun setupUI() {
        with(binding) {
            loginBtn.setOnSafeClickListener {
                val isValid = viewModel.validate(
                    signUpUsernameED.text.toString(),
                    signUpPasswordEd.text.toString()
                )
                if (isValid) {
                    viewModel.login(
                        SignUserAppRequestModel(
                            signUpUsernameED.text.toString(),
                            signUpPasswordEd.text.toString()
                        )
                    )
                } else {
                    val errorDialog = buildErrorDialog(getString(R.string.sign_up_error))
                    errorDialog.show()
                }
            }
        }
    }

    companion object {
        fun newInstance() = SignInFragment()
    }
}