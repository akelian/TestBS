package by.devnmisko.testbs.ui.photodetail

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.devnmisko.domain.model.Output
import by.devnmisko.testbs.MainActivity
import by.devnmisko.testbs.R
import by.devnmisko.testbs.databinding.FragmentImageDetailBinding
import by.devnmisko.testbs.ui.base.BaseFragment
import by.devnmisko.testbs.utils.Const.INVALID_ID
import by.devnmisko.testbs.utils.Const.Range.COMMENT_RANGE
import by.devnmisko.testbs.utils.collectLatestWhenStarted
import by.devnmisko.testbs.utils.getDate
import by.devnmisko.testbs.utils.hide
import by.devnmisko.testbs.utils.show
import com.bumptech.glide.Glide
import javax.inject.Inject

class ImageDetailFragment : BaseFragment<FragmentImageDetailBinding>(), OnCommentLongPressListener {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentImageDetailBinding
        get() = FragmentImageDetailBinding::inflate

    @Inject
    lateinit var viewModelFactory: ImageDetailViewModel.Factory
    private lateinit var viewModel: ImageDetailViewModel

    private var imageId: Int = INVALID_ID
    private lateinit var imageUrl: String
    private var imageDate: Long = INVALID_ID.toLong()

    private val adapter by lazy {
        CommentsAdapter(this)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            imageId = it.getInt(IMAGE_ID)
            imageUrl = it.getString(IMAGE_URL, "")
            imageDate = it.getLong(IMAGE_DATE_IN_MILLIS)
        }
        (activity as MainActivity).appComponent.inject(this)
        viewModel = viewModelFactory.create(imageId)
    }

    override fun setupUI() {
        with(binding){
            Glide.with(this@ImageDetailFragment).load(imageUrl).into(image)
            date.text = getDate(imageDate)
            commentsList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, true)
            commentsList.adapter= adapter
            adapter.addLoadStateListener { state ->
                progress.isVisible = state.refresh == LoadState.Loading
                progress.isVisible = state.append == LoadState.Loading
                if ( state.append.endOfPaginationReached )
                {
                    if ( adapter.itemCount < 1)
                        emptyListMessage.show()
                    else
                        emptyListMessage.hide()
                }
            }
            commentInput.setEndIconOnClickListener {
                postComment()
            }
        }

        subscribeUI()
    }

    private fun subscribeUI() {
        viewLifecycleOwner.collectLatestWhenStarted(viewModel.allComments) { comments ->
            adapter.submitData(comments)
        }

        viewLifecycleOwner.collectLatestWhenStarted(viewModel.postCommentState) { output ->
            output?.let {
                when(output.status) {
                    Output.Status.SUCCESS -> {
                        binding.commentED.text?.clear()
                        adapter.refresh()
                        binding.progress.hide()
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

        viewLifecycleOwner.collectLatestWhenStarted(viewModel.postCommentState) { output ->
            output?.let {
                when(output.status) {
                    Output.Status.SUCCESS -> {
                        binding.commentED.text?.clear()
                        adapter.refresh()
                        binding.progress.hide()
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
                        buildErrorDialog(output.message)
                    }

                    Output.Status.LOADING -> {
                        binding.progress.show()
                    }
                }
            }
        }
    }

    private fun postComment() {
            val comment = binding.commentED.text.toString()
            if(comment.length in COMMENT_RANGE){
                viewModel.postComment(comment)
            }
    }

    private fun buildConfirmDialog(commentId: Int): AlertDialog.Builder {
        return AlertDialog.Builder(context).apply {
            setMessage(getString(R.string.please_confirm_removing))
            setNegativeButton(
                getString(R.string.cancel)
            ) { dialog, _ -> dialog.dismiss() }
            setPositiveButton(getString(R.string.remove)) { _, _ ->
                viewModel.removeComment(commentId)
            }
            create()
        }
    }
    override fun removeComment(commentId: Int) {
        if (commentId != INVALID_ID) {
            buildConfirmDialog(commentId).show()
        }
    }

    companion object {
        const val IMAGE_ID = "image_id"
        const val IMAGE_URL = "image_url"
        const val IMAGE_DATE_IN_MILLIS = "image_date"
    }
}
