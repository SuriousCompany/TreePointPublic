package company.surious.treepoint.ui.common.fragments.create_tree_point

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import company.surious.domain.entities.TreeType
import company.surious.treepoint.R
import company.surious.treepoint.ui.common.custom_views.base_spinner.BaseSpinnerAdapter

class TreeTypeSpinnerAdapter(context: Context) :
    BaseSpinnerAdapter<TreeType>(
        context,
        R.layout.item_tree_type,
        R.layout.item_tree_type_dropdown
    ) {

    override fun bindView(view: View, item: TreeType, isSelected: Boolean) {
        bindCommonViews(view, item, isSelected)
        val triangleImageView = view.findViewById<ImageView>(R.id.treeTypeTriangleImageView)
        triangleImageView.visibility = if (isSelected) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

    override fun bindDropdownView(view: View, item: TreeType, isSelected: Boolean) {
        bindCommonViews(view, item, isSelected)
    }

    private fun bindCommonViews(view: View, item: TreeType, isSelected: Boolean) {
        //TODO set icon
        val textView = view.findViewById<TextView>(R.id.treeTypeTextView)
        textView.text = item.typeName //TODO apply localization
        view.setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                if (isSelected) {
                    R.color.backgroundColor
                } else {
                    R.color.white
                }
            )
        )
    }
}