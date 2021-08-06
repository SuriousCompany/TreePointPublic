package company.surious.treepoint.ui.common.fragments.photo

import com.bumptech.glide.Glide
import com.google.firebase.storage.StorageReference
import company.surious.treepoint.R
import company.surious.treepoint.databinding.ItemPhotoBinding
import company.surious.treepoint.ui.common.binding.lists.BindableRecyclerAdapter
import company.surious.treepoint.ui.common.binding.lists.BindableViewHolder

class StorageReferenceAdapter :
    BindableRecyclerAdapter<ItemPhotoBinding, StorageReference, StorageReferenceAdapter.StorageReferencePhotoViewHolder>() {

    override val itemLayoutRes: Int = R.layout.item_photo

    override fun createViewHolder(binding: ItemPhotoBinding) =
        StorageReferencePhotoViewHolder(binding)

    inner class StorageReferencePhotoViewHolder(binding: ItemPhotoBinding) :
        BindableViewHolder<ItemPhotoBinding, StorageReference>(binding) {

        override fun bind(item: StorageReference) {
            Glide.with(binding.root)
                .load(item)
                .fitCenter()
                .into(binding.photoImageView)
        }
    }
}