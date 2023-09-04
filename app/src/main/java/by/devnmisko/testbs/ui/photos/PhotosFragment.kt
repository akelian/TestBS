package by.devnmisko.testbs.ui.photos

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import by.devnmisko.domain.model.ImageDomainResponseModel
import by.devnmisko.domain.model.Output
import by.devnmisko.testbs.MainActivity
import by.devnmisko.testbs.R
import by.devnmisko.testbs.databinding.FragmentPhotosBinding
import by.devnmisko.testbs.ui.base.BaseFragment
import by.devnmisko.testbs.ui.photodetail.ImageDetailFragment
import by.devnmisko.testbs.utils.Const.INVALID_ID
import by.devnmisko.testbs.utils.collectLatestWhenStarted
import by.devnmisko.testbs.utils.hide
import by.devnmisko.testbs.utils.show
import javax.inject.Inject

class PhotosFragment : BaseFragment<FragmentPhotosBinding>(), OnImageLongPressListener,
    OnImageClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PhotosViewModel by viewModels { viewModelFactory }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPhotosBinding
        get() = FragmentPhotosBinding::inflate

    private val adapter by lazy {
        ImagesAdapter(this, this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        adapter.refresh()
    }

    override fun setupUI() {
        with(binding) {
            addPhotoBtn.setOnClickListener {
                openCamera()
            }
            photoList.layoutManager = GridLayoutManager(requireContext(), 3)
            photoList.adapter = adapter
            adapter.addLoadStateListener { state ->
                progress.isVisible = state.refresh == LoadState.Loading
                progress.isVisible = state.append == LoadState.Loading
            }
        }
        subscribeUI()
    }

    private fun subscribeUI() {

        viewLifecycleOwner.collectLatestWhenStarted(viewModel.pagingImages) { images ->
            adapter.submitData(images)
        }

        viewLifecycleOwner.collectLatestWhenStarted(viewModel.removeState) { output ->
            output?.let {
                when (output.status) {
                    Output.Status.SUCCESS -> {
                        binding.progress.hide()
                        Toast.makeText(
                            context,
                            getString(R.string.image_successfully_remove),
                            Toast.LENGTH_SHORT
                        ).show()
                        adapter.refresh()
                    }

                    Output.Status.ERROR -> {
                        binding.progress.hide()
                        buildErrorDialog(output.message).show()
                    }

                    Output.Status.LOADING -> {
                        binding.progress.show()
                    }
                }
            }
        }
    }

    private fun openCamera() {
        findNavController().navigate(R.id.action_photosFragment_to_cameraFragment)
    }

    override fun removeImage(id: Int?) {
        id?.let {
            buildConfirmDialog(id).show()
        }
    }

    private fun buildConfirmDialog(id: Int): AlertDialog.Builder {
        return AlertDialog.Builder(context).apply {
            setMessage(getString(R.string.please_confirm_removing))
            setNegativeButton(
                getString(R.string.cancel)
            ) { dialog, _ -> dialog.dismiss() }
            setPositiveButton(getString(R.string.remove)) { _, _ ->
                viewModel.removeImage(id)
            }
            create()
        }
    }

    override fun openImageDetail(image: ImageDomainResponseModel?) {
        val bundle = Bundle().apply {
            with(ImageDetailFragment) {
                putString(IMAGE_URL, image?.url)
                putInt(IMAGE_ID, image?.id ?: INVALID_ID)
                putLong(IMAGE_DATE_IN_MILLIS, image?.date ?: INVALID_ID.toLong())
            }
        }
        findNavController().navigate(R.id.action_photosFragment_to_imageDetailFragment, bundle)
    }

}