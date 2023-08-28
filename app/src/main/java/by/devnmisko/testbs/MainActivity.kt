package by.devnmisko.testbs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.devnmisko.testbs.databinding.ActivityMainBinding
import by.devnmisko.testbs.di.ApplicationComponent
import by.devnmisko.testbs.ui.mainscreen.MenuFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var appComponent: ApplicationComponent

    private lateinit var navMainController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent = (applicationContext as App).getComponent()
        appComponent.inject(this)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment
        navMainController = navHostFragment.navController
    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.title = getString(R.string.empty_string)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuFragment =
            supportFragmentManager.fragments[0].childFragmentManager.fragments[0] as MenuFragment
        if (item.itemId == android.R.id.home) menuFragment.openDrawer()
        return super.onOptionsItemSelected(item)
    }

}