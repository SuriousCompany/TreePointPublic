package company.surious.treepoint.ui.common.binding.lists.range

import androidx.databinding.ViewDataBinding
import company.surious.domain.errors.UiError
import company.surious.domain.logging.logDebug
import company.surious.treepoint.ui.common.binding.lists.BindableRecyclerAdapter
import company.surious.treepoint.ui.common.binding.lists.BindableViewHolder
import company.surious.treepoint.ui.common.binding.lists.RecyclerViewEventHandler

abstract class BindableRangeAdapter<Binding : ViewDataBinding, Item : SelectableItem, ViewHolder : BindableViewHolder<Binding, Item>> :
    BindableRecyclerAdapter<Binding, Item, ViewHolder>() {

    protected open var firstSelectedItemPosition = -1
    protected open var lastSelectedItemPosition = -1

    protected open var isLastClickEndTheEndOfSelection = false

    override var eventHandler: RecyclerViewEventHandler<Item>? = null
        set(value) {
            if (value is RangeRecyclerViewEventHandler<Item>) {
                field = value
            } else {
                throw UiError(
                    null,
                    "Please use RangeRecyclerViewEventHandler with BindableRangeAdapter"
                )
            }
        }

    private val rangeEventHandler: RangeRecyclerViewEventHandler<Item>?
        get() = eventHandler as RangeRecyclerViewEventHandler<Item>?

    override fun onItemClicked(index: Int, item: Item) {
        super.onItemClicked(index, item)
        if (firstSelectedItemPosition == -1 && lastSelectedItemPosition == -1) {
            logDebug("clicked first item: $index")
            selectRange(index, index)
        } else {
            when {
                index < firstSelectedItemPosition -> {
                    logDebug("clicked left item: $index")
                    selectRange(index, lastSelectedItemPosition)
                }
                index > lastSelectedItemPosition -> {
                    logDebug("clicked right item: $index")
                    selectRange(firstSelectedItemPosition, index)
                }
                else -> {
                    logDebug("clicked item in range: $index")
                    handleClickInSelectedRange(index)
                }
            }
        }
    }

    protected open fun handleClickInSelectedRange(index: Int) {
        val itemsCountBeforeClick = index - firstSelectedItemPosition
        val itemsCountAfterClick = lastSelectedItemPosition - index
        when {
            itemsCountBeforeClick < itemsCountAfterClick -> {
                logDebug("clicked item in left range")
                isLastClickEndTheEndOfSelection = false
                unselectRange(firstSelectedItemPosition, index)
            }
            itemsCountBeforeClick > itemsCountAfterClick -> {
                logDebug("clicked item in right range")
                isLastClickEndTheEndOfSelection = true
                unselectRange(index, lastSelectedItemPosition)
            }
            else -> {
                logDebug("clicked item in the middle of the range")
                if (isLastClickEndTheEndOfSelection) {
                    logDebug("unselecting right half")
                    unselectRange(index, lastSelectedItemPosition)
                } else {
                    logDebug("unselecting left half")
                    unselectRange(firstSelectedItemPosition, index)
                }
            }
        }
    }

    protected open fun selectRange(start: Int, end: Int) {
        logDebug("select range $start, $end")
        for (i: Int in start..end) {
            data[i].isSelected = true
        }
        var notifyEventHandler = false
        when {
            start == end -> {
                notifyEventHandler = true
                firstSelectedItemPosition = start
                lastSelectedItemPosition = end
            }
            start < firstSelectedItemPosition -> {
                notifyEventHandler = true
                firstSelectedItemPosition = start
                if (lastSelectedItemPosition == -1) {
                    lastSelectedItemPosition = end
                }
                isLastClickEndTheEndOfSelection = false
            }
            end > lastSelectedItemPosition -> {
                notifyEventHandler = true
                lastSelectedItemPosition = end
                if (firstSelectedItemPosition == -1) {
                    firstSelectedItemPosition = start
                }
                isLastClickEndTheEndOfSelection = true
            }
        }
        notifyItemRangeChanged(start, (end + 1) - start)//TODO optimize it
        if (notifyEventHandler) {
            notifyEventHandler()
        }
    }

    protected open fun unselectRange(start: Int, end: Int) {
        logDebug("unselect range $start, $end")
        //TODO update isLastClickEndTheEndOfSelection
        for (i: Int in start..end) {
            data[i].isSelected = false
        }

        when {
            //TODO handle full unselection
            start == firstSelectedItemPosition -> firstSelectedItemPosition = end + 1
            end == lastSelectedItemPosition -> lastSelectedItemPosition = start - 1
        }
        notifyItemRangeChanged(start, (end + 1) - start)//TODO optimize it
        if (lastSelectedItemPosition < firstSelectedItemPosition) {
            lastSelectedItemPosition = -1
            firstSelectedItemPosition = -1
        }
        notifyEventHandler()
    }

    protected open fun notifyEventHandler() {
        if (firstSelectedItemPosition != -1 && lastSelectedItemPosition != -1) {
            rangeEventHandler?.onRangeSelected(
                data[firstSelectedItemPosition],
                data[lastSelectedItemPosition]
            )
        } else {
            rangeEventHandler?.onNothingSelected()
        }
    }
}