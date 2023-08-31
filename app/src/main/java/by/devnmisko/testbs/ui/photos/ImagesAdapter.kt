package by.devnmisko.testbs.ui.photos

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.devnmisko.domain.model.ImageDomainResponseModel
import by.devnmisko.testbs.databinding.ItemPhotoImageBinding
import by.devnmisko.testbs.utils.getDate
import com.bumptech.glide.Glide


class ImagesAdapter(private val onImageLongPressListener: OnImageLongPressListener) :
    PagingDataAdapter<ImageDomainResponseModel, ImagesAdapter.ViewHolder>(DIFF_CALLBACK) {
    lateinit var context : Context

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        context = recyclerView.context
        super.onAttachedToRecyclerView(recyclerView)
    }

    class ViewHolder(val binding: ItemPhotoImageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPhotoImageBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = getPhoto(position)
        with(photo){
            Glide.with(context).load(this?.url).placeholder(ColorDrawable(Color.GRAY)).into(holder.binding.photo)
            holder.binding.photoName.text = getDate(this?.date ?: 0)
            holder.binding.root.setOnLongClickListener {
                onImageLongPressListener.removeImage(this?.id)
                true // returning true instead of false, works for me
            }

        }

    }

    private fun getPhoto(position: Int): ImageDomainResponseModel? {
        return getItem(position)
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ImageDomainResponseModel>() {
            override fun areItemsTheSame(
                oldItem: ImageDomainResponseModel,
                newItem: ImageDomainResponseModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ImageDomainResponseModel,
                newItem: ImageDomainResponseModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}