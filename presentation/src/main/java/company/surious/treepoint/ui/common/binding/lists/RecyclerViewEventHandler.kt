package company.surious.treepoint.ui.common.binding.lists

interface RecyclerViewEventHandler<T> {
    fun onItemClicked(index: Int, item: T) {}
    fun onItemLongClicked(index: Int, item: T) {}
}