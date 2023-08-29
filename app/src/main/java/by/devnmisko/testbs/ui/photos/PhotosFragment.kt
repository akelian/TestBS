package by.devnmisko.testbs.ui.photos

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.devnmisko.domain.model.Output
import by.devnmisko.testbs.MainActivity
import by.devnmisko.testbs.R
import by.devnmisko.testbs.databinding.FragmentPhotosBinding
import by.devnmisko.testbs.ui.base.BaseFragment
import by.devnmisko.testbs.utils.collectLatestWhenStarted
import by.devnmisko.testbs.utils.hide
import by.devnmisko.testbs.utils.show
import javax.inject.Inject

class PhotosFragment : BaseFragment<FragmentPhotosBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PhotosViewModel by viewModels { viewModelFactory }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPhotosBinding
        get() = FragmentPhotosBinding::inflate

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }


    override fun setupUI() {
        viewModel.fetchImages(0)
        binding.addPhotoBtn.setOnClickListener {
            openCamera()
        }
        subscribeUI()
    }

    private fun subscribeUI() {
        viewLifecycleOwner.collectLatestWhenStarted(viewModel.imagesList) { output ->
            output?.let {
                when (output.status) {
                    Output.Status.SUCCESS -> {
                        if (output.data?.isEmpty() == true) {
                            binding.emptyListMessage.show()
                        }
                    }

                    Output.Status.ERROR -> {
                        val errorDialog = buildErrorDialog(output.message)
                        errorDialog.show()
                    }

                    Output.Status.LOADING -> {}
                }
            }
        }

        viewLifecycleOwner.collectLatestWhenStarted(viewModel.loadingState) { isLoading ->
            if (isLoading) {
                binding.progress.show()
                binding.emptyListMessage.hide()
                binding.photoList.hide()
            } else {
                binding.progress.hide()
            }
        }
    }

    private fun openCamera() {
        findNavController().navigate(R.id.action_photosFragment_to_cameraFragment)
    }

}