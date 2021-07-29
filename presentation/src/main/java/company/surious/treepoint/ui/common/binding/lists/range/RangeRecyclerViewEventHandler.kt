package company.surious.treepoint.ui.common.binding.lists.range

import company.surious.treepoint.ui.common.binding.lists.RecyclerViewEventHandler

interface RangeRecyclerViewEventHandler<T> : RecyclerViewEventHandler<T> {
    fun onRangeSelected(firstItem: T, lastItem: T) {

    }

    fun onNothingSelected() {

    }
}