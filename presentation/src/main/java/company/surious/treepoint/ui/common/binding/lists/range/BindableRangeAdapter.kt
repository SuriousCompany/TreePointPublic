package company.surious.treepoint.ui.common.binding.lists.range

import androidx.databinding.ViewDataBinding
import company.surious.domain.errors.UiError
import company.surious.domain.logging.LogTags
import company.surious.domain.logging.logVerbose
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
            logVerbose(LogTags.DEBUG, "clicked first item: $index")
            selectRange(index, index)
        } else {
            when {
                index < firstSelectedItemPosition -> {
                    logVerbose(LogTags.DEBUG, "clicked left item: $index")
                    selectRange(index, lastSelectedItemPosition)
                }
                index > lastSelectedItemPosition -> {
                    logVerbose(LogTags.DEBUG, "clicked right item: $index")
                    selectRange(firstSelectedItemPosition, index)
                }
                else -> {
                    logVerbose(LogTags.DEBUG, "clicked item in range: $index")
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
                logVerbose(LogTags.DEBUG, "clicked item in left range")
                isLastClickEndTheEndOfSelection = false
                unselectRange(firstSelectedItemPosition, index)
            }
            itemsCountBeforeClick > itemsCountAfterClick -> {
                logVerbose(LogTags.DEBUG, "clicked item in right range")
                isLastClickEndTheEndOfSelection = true
                unselectRange(index, lastSelectedItemPosition)
            }
            else -> {
                logVerbose(LogTags.DEBUG, "clicked item in the middle of the range")
                if (isLastClickEndTheEndOfSelection) {
                    logVerbose(LogTags.DEBUG, "unselecting right half")
                    unselectRange(index, lastSelectedItemPosition)
                } else {
                    logVerbose(LogTags.DEBUG, "unselecting left half")
                    unselectRange(firstSelectedItemPosition, index)
                }
            }
        }
    }

    protected open fun selectRange(start: Int, end: Int) {
        logVerbose(LogTags.DEBUG, "select range $start, $end")
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
        logVerbose(LogTags.DEBUG, "unselect range $start, $end")
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