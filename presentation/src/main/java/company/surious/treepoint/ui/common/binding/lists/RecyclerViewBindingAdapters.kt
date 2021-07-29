package company.surious.treepoint.ui.common.binding.lists

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import company.surious.domain.errors.UiError

@Suppress("UNCHECKED_CAST")
@BindingAdapter(
    value = ["recyclerAdapter", "recyclerData", "recyclerLayoutManager", "recyclerEventHandler"],
    requireAll = false
)
fun <T : Any> bindRecyclerData(
    view: RecyclerView,
    adapter: BindableRecyclerAdapter<*, T, *>,
    data: LiveData<List<T>>,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(view.context),
    eventHandler: RecyclerViewEventHandler<T>? = null
) {
    view.adapter = adapter
    view.layoutManager = layoutManager
    val lifecycleOwner = view.findViewTreeLifecycleOwner() ?: throw(UiError(
        null,
        "No lifecycle owner for this RecyclerView"
    ))
    data.observe(lifecycleOwner, Observer(adapter::setData))
    adapter.eventHandler = eventHandler
}