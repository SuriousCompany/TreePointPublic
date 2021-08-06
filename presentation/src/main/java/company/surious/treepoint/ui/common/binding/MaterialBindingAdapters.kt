package company.surious.treepoint.ui.common.binding

import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("isVisible")
fun bindSignInOnClick(button: FloatingActionButton, isVisible: Boolean) {
    if (isVisible) {
        button.show()
    } else {
        button.hide()
    }
}