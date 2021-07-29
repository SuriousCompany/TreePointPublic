package company.surious.treepoint.ui.common.fragments.dialogs.month_picker

import company.surious.treepoint.R
import company.surious.treepoint.databinding.ItemSelectableMonthBinding
import company.surious.treepoint.ui.common.binding.lists.BindableViewHolder
import company.surious.treepoint.ui.common.binding.lists.range.BindableRangeAdapter

class SelectableMonthAdapter :
    BindableRangeAdapter<ItemSelectableMonthBinding, SelectableMonth, SelectableMonthAdapter.SelectableMonthViewHolder>() {

    override val itemLayoutRes: Int = R.layout.item_selectable_month

    override fun createViewHolder(binding: ItemSelectableMonthBinding): SelectableMonthViewHolder =
        SelectableMonthViewHolder(binding)

    fun selectMonthRange(startMonth: Int, endMonth: Int) {
        selectRange(startMonth, endMonth)
    }

    inner class SelectableMonthViewHolder(binding: ItemSelectableMonthBinding) :
        BindableViewHolder<ItemSelectableMonthBinding, SelectableMonth>(binding) {

        override fun bind(item: SelectableMonth) {
            binding.month = item
        }
    }
}