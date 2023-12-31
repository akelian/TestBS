package by.devnmisko.testbs.ui.loginscreen

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import by.devnmisko.domain.model.Output
import by.devnmisko.domain.model.SignUserDomainResponseModel
import by.devnmisko.testbs.MainActivity
import by.devnmisko.testbs.R
import by.devnmisko.testbs.databinding.FragmentStartupBinding
import by.devnmisko.testbs.ui.base.BaseFragment
import by.devnmisko.testbs.utils.collectLatestWhenStarted
import by.devnmisko.testbs.utils.hideKeyboard
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StartupFragment : BaseFragment<FragmentStartupBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: StartUpFragmentViewModel by viewModels { viewModelFactory }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStartupBinding
        get() = FragmentStartupBinding::inflate

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.hide()
    }

    override fun setupUI() {
        subscribeUI()

        val fragmentList = mutableListOf<Pair<Fragment, String>>()
        fragmentList.add(Pair(SignInFragment.newInstance(), getString(R.string.login)))
        fragmentList.add(Pair(SignUpFragment.newInstance(), getString(R.string.register)))

        val pager = binding.pager
        val pagerAdapter = PagerAdapter(this, fragmentList)
        val tabLayout = binding.tabLayout
        pager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = fragmentList[position].second
        }.attach()
    }

    private fun subscribeUI() {
        val dialog = buildProgressDialog()
        viewLifecycleOwner.collectLatestWhenStarted(viewModel.loginState) { output: Output<SignUserDomainResponseModel>? ->
            output?.let {
                when (output.status) {
                    Output.Status.SUCCESS -> {
                        findNavController().navigate(
                            R.id.action_startupFragment_to_mainFragment,
                        )
                    }

                    Output.Status.ERROR -> {
                        val errorDialog = buildErrorDialog(output.message)
                        errorDialog.show()
                    }

                    else -> {}
                }
            }
        }

        viewLifecycleOwner.collectLatestWhenStarted(viewModel.signUpState) { output: Output<SignUserDomainResponseModel>? ->
            output?.let {
                when (output.status) {
                    Output.Status.SUCCESS -> {
                        Toast.makeText(context,
                            getString(R.string.account_created), Toast.LENGTH_SHORT).show()
                        viewModel.viewModelScope.launch {
                            delay(1000)
                            withContext(Dispatchers.Main) {
                                binding.pager.setCurrentItem(0, true)
                            }
                        }
                    }

                    Output.Status.ERROR -> {
                        dialog.dismiss()
                        val errorDialog = buildErrorDialog(output.message)
                        errorDialog.show()
                    }

                    else -> {}
                }
            }
        }

        viewLifecycleOwner.collectLatestWhenStarted(viewModel.loadingState) { isLoading ->
            if (isLoading) {
                dialog.show()
            } else {
                dialog.dismiss()
                hideKeyboard()
            }
        }
    }

}