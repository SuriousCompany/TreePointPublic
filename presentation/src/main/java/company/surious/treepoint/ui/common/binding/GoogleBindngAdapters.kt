package company.surious.treepoint.ui.common.binding

import androidx.databinding.BindingAdapter
import com.google.android.gms.common.SignInButton

@BindingAdapter("android:onClick")
fun bindSignInOnClick(button: SignInButton, method: () -> Unit) {
    button.setOnClickListener { method.invoke() }
}