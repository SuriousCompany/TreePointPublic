package company.surious.treepoint.ui.common.binding.lists

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BindableViewHolder<Binding : ViewDataBinding, Item : Any>(var binding: Binding) :
    RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: Item)
}