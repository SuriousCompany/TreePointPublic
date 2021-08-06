package company.surious.treepoint.ui.common.fragments.photo

import android.net.Uri
import com.bumptech.glide.Glide
import company.surious.treepoint.R
import company.surious.treepoint.databinding.ItemPhotoBinding
import company.surious.treepoint.ui.common.binding.lists.BindableRecyclerAdapter
import company.surious.treepoint.ui.common.binding.lists.BindableViewHolder

class PhotoAdapter :
    BindableRecyclerAdapter<ItemPhotoBinding, Uri, PhotoAdapter.PhotoViewHolder>() {

    override val itemLayoutRes: Int = R.layout.item_photo

    override fun createViewHolder(binding: ItemPhotoBinding) = PhotoViewHolder(binding)

    fun add(uri: Uri) {
        data.add(uri)
        notifyItemInserted(data.size - 1)
    }

    fun remove(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class PhotoViewHolder(binding: ItemPhotoBinding) :
        BindableViewHolder<ItemPhotoBinding, Uri>(binding) {

        override fun bind(item: Uri) {
            Glide.with(binding.root)
                .load(item)
                //.sizeMultiplier(0.3f)
                .fitCenter()
                .into(binding.photoImageView)
        }
    }
}