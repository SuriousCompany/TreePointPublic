package company.surious.treepoint.ui.common.fragments.dialogs.month_picker

import company.surious.treepoint.ui.common.binding.lists.range.SelectableItem

data class SelectableMonth(val name: String, override var isSelected: Boolean = false) :
    SelectableItem {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SelectableMonth) return false
        if (name != other.name) return false
        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}