package by.devnmisko.testbs.ui.mainscreen

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import by.devnmisko.testbs.MainActivity
import by.devnmisko.testbs.R
import by.devnmisko.testbs.databinding.FragmentMenuBinding
import by.devnmisko.testbs.ui.base.BaseFragment
import by.devnmisko.testbs.ui.map.MapFragment
import kotlinx.coroutines.delay
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.schedule
import kotlin.concurrent.thread

class MenuFragment : BaseFragment<FragmentMenuBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MenuFragmentViewModel by viewModels { viewModelFactory }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMenuBinding
        get() = FragmentMenuBinding::inflate
    private lateinit var navMainController: NavController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
        var backPressCounter = 0
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            if(backPressCounter == 0){
                Toast.makeText(activity,
                    getString(R.string.tap_back_button_twice_to_exit), Toast.LENGTH_SHORT).show()
                backPressCounter++
            } else {
                (activity as MainActivity).finish()
            }
            thread {
                Timer().schedule(2000) {
                    backPressCounter = 0
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }


    override fun setupUI() {
        with(activity as MainActivity) {
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.setHomeAsUpIndicator(R.drawable.ic_menu)
            }
        }
        (binding.navigation.getHeaderView(0) as TextView).text = viewModel.getUsername()
        val navHostFragment =
            childFragmentManager.findFragmentById(binding.navFragmentContainer.id) as NavHostFragment
        navMainController = navHostFragment.navController
        AppBarConfiguration(navMainController.graph, binding.drawerLayout)
        binding.navigation.setupWithNavController(navMainController)

    }

    fun openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

}