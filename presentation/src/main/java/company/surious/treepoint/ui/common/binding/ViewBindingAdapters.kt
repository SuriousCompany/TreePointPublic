package company.surious.treepoint.ui.common.binding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("setVisibility")
internal fun setVisibility(view: View, visible: Boolean) {
    view.visibility =
        if (visible) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
}

@BindingAdapter("setVisibilityWithGone")
internal fun setVisibilityWithGone(view: View, visible: Boolean) {
    view.visibility =
        if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
}
