package company.surious.treepoint.ui.common.binding.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BindableRecyclerAdapter<Binding : ViewDataBinding, Item : Any, ViewHolder : BindableViewHolder<Binding, Item>>
    : RecyclerView.Adapter<ViewHolder>() {

    protected abstract val itemLayoutRes: Int

    val data = ArrayList<Item>()

    open var eventHandler: RecyclerViewEventHandler<Item>? = null

    fun setData(data: List<Item>) {
        val copy = ArrayList(data)
        this.data.clear()
        this.data.addAll(copy)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val adapterPosition = holder.bindingAdapterPosition
        val item = data[adapterPosition]
        eventHandler?.let { handler ->
            holder.binding.root.setOnClickListener {
                val index = data.indexOf(item)
                onItemClicked(index, item)
                handler.onItemClicked(adapterPosition, item)
            }
            holder.binding.root.setOnLongClickListener {
                val index = data.indexOf(item)
                handler.onItemLongClicked(index, item)
                onItemLongClicked(adapterPosition, item)
            }
        }
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: Binding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                itemLayoutRes,
                parent,
                false
            )
        return createViewHolder(binding)
    }

    protected abstract fun createViewHolder(binding: Binding): ViewHolder

    protected open fun onItemClicked(index: Int, item: Item) {}

    protected open fun onItemLongClicked(index: Int, item: Item): Boolean = false

    protected open fun notifyItemChanged(item: Item) {
        val index = data.indexOf(item)
        if (index != -1) {
            notifyItemChanged(index)
        }
    }
}