package by.devnmisko.testbs.ui.photos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.devnmisko.testbs.R
import by.devnmisko.testbs.databinding.FragmentPhotosBinding
import by.devnmisko.testbs.ui.base.BaseFragment

class PhotosFragment : BaseFragment<FragmentPhotosBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPhotosBinding
        get() = FragmentPhotosBinding::inflate

    override fun setupUI() {

    }


}