package by.devnmisko.testbs.ui.loginscreen

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(
    fragment: Fragment,
    private val fragmentList: List<Pair<Fragment, String>>
) : FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position].first
    }
}