package company.surious.treepoint.ui.common.binding

import android.widget.NumberPicker
import androidx.databinding.BindingAdapter

@BindingAdapter("bindValues")
fun bindValues(picker: NumberPicker, values: List<String>) {
    bindValues(picker, values.toTypedArray())
}

@BindingAdapter("bindValues")
fun bindValues(picker: NumberPicker, values: Array<String>) {
    with(picker) {
        minValue = 0
        maxValue = values.size - 1
        displayedValues = values
    }
}

@BindingAdapter("bindOnValueChangedListener")
fun bindOnValueChangedListener(picker: NumberPicker, listener: (Int) -> Unit) {
    picker.setOnValueChangedListener { _, _, value ->
        listener.invoke(value)
    }
}