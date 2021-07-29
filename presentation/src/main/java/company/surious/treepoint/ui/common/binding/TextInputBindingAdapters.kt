package company.surious.treepoint.ui.common.binding

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorText")
fun bindErrorText(layout: TextInputLayout, text: String) {
    layout.error = text
}
