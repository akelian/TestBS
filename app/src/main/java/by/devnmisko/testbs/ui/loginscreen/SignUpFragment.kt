package by.devnmisko.testbs.ui.loginscreen


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import by.devnmisko.domain.model.Output
import by.devnmisko.domain.model.SignUserDomainResponseModel
import by.devnmisko.testbs.MainActivity
import by.devnmisko.testbs.R
import by.devnmisko.testbs.databinding.FragmentSignupBinding
import by.devnmisko.testbs.model.SignUserAppRequestModel
import by.devnmisko.testbs.ui.base.BaseFragment
import by.devnmisko.testbs.utils.collectLatestWhenStarted
import by.devnmisko.testbs.utils.setOnSafeClickListener
import javax.inject.Inject

class SignUpFragment : BaseFragment<FragmentSignupBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: StartUpFragmentViewModel by viewModels { viewModelFactory }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSignupBinding
        get() = FragmentSignupBinding::inflate

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    override fun setupUI() {
        with(binding){
            signUpBtn.setOnSafeClickListener {
                val isValid = viewModel.validate(
                    signUpUsernameED.text.toString(),
                    signUpPasswordEd.text.toString(),
                    signUpConfirmEd.text.toString()
                )
                if (isValid) {
                    viewModel.signUp(
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
        fun newInstance() = SignUpFragment()
    }
}