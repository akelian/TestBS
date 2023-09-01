package by.devnmisko.testbs.ui.photodetail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.devnmisko.domain.model.CommentDomainResponseModel
import by.devnmisko.testbs.databinding.ItemCommentBinding
import by.devnmisko.testbs.utils.Const.INVALID_ID
import by.devnmisko.testbs.utils.getDateTime

class CommentsAdapter(private val onCommentLongPressListener: OnCommentLongPressListener) :
    PagingDataAdapter<CommentDomainResponseModel, CommentsAdapter.ViewHolder>(DIFF_CALLBACK) {
    lateinit var context: Context
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        context = recyclerView.context
        super.onAttachedToRecyclerView(recyclerView)
    }

    class ViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = getComment(position)
        with(comment) {
            holder.binding.commentBody.text = comment?.text
            holder.binding.commentDate.text = getDateTime(this?.date ?: 0)
            holder.binding.root.setOnLongClickListener {
                onCommentLongPressListener.removeComment(comment?.id ?: INVALID_ID)
                true
            }
        }
    }

    private fun getComment(position: Int): CommentDomainResponseModel? {
        return getItem(position)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CommentDomainResponseModel>() {
            override fun areItemsTheSame(
                oldItem: CommentDomainResponseModel, newItem: CommentDomainResponseModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CommentDomainResponseModel, newItem: CommentDomainResponseModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}