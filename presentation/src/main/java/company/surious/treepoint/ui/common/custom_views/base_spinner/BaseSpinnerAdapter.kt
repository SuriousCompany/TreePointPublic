package company.surious.treepoint.ui.common.custom_views.base_spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes

abstract class BaseSpinnerAdapter<T>(
    context: Context,
    @LayoutRes private val viewLayout: Int,
    @LayoutRes private val dropdownViewLayout: Int = viewLayout
) : ArrayAdapter<T>(context, viewLayout) {

    private var selectedItemPosition = -1

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View =
        getView(false, position, convertView, parent)

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View =
        getView(true, position, convertView, parent)

    fun setData(data: List<T>) {
        val copy = ArrayList(data)
        clear()
        addAll(copy)
    }

    fun notifyItemSelected(position: Int) {
        selectedItemPosition = position
        notifyDataSetChanged()
    }

    private fun getView(
        isDropdown: Boolean,
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view = convertView ?: createView(
            if (isDropdown) {
                dropdownViewLayout
            } else {
                viewLayout
            }, parent
        )
        val item = getItem(position)!!
        if (isDropdown) {
            bindDropdownView(view, item, position == selectedItemPosition)
        } else {
            bindView(view, item, position == selectedItemPosition)
        }
        return view
    }

    private fun createView(@LayoutRes viewLayout: Int, parent: ViewGroup): View =
        LayoutInflater.from(parent.context).inflate(
            viewLayout,
            parent,
            false
        )

    protected abstract fun bindView(view: View, item: T, isSelected: Boolean)
    protected abstract fun bindDropdownView(view: View, item: T, isSelected: Boolean)

}