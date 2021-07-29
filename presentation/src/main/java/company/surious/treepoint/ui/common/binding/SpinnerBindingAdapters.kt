package company.surious.treepoint.ui.common.binding

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import company.surious.treepoint.ui.common.custom_views.base_spinner.BaseSpinnerAdapter

@Suppress("UNCHECKED_CAST")
@BindingAdapter(
    value = ["spinnerAdapter", "spinnerData", "spinnerSelectionListener"],
    requireAll = false
)
fun bindAdapter(
    spinner: Spinner,
    adapter: BaseSpinnerAdapter<*>,
    data: List<*>,
    itemSelectedListener: (Any) -> Unit
) {
    spinner.adapter = adapter
    adapter.setData(data as List<Nothing>)
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            adapter.notifyItemSelected(position)
            itemSelectedListener.invoke(adapter.getItem(position)!!)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }
    }
}
