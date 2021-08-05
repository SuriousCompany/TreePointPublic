package company.surious.treepoint.ui.common.binding

import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("movementMethod")
fun bindMovementMethods(layout: TextView, movementMethod: MovementMethod) {
    layout.movementMethod = movementMethod
}

@BindingAdapter("linkMovementMethod")
fun bindLinkMovementMethod(layout: TextView, enabled: Boolean) {
    if (enabled) {
        layout.movementMethod = LinkMovementMethod.getInstance()
    }
}