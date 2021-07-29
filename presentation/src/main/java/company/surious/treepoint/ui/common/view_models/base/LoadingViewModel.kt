package company.surious.treepoint.ui.common.view_models.base

import androidx.lifecycle.MutableLiveData

open class LoadingViewModel : DisposableViewModel() {
    protected open var isLoadingByDefault = true
    open val isLoading = MutableLiveData<Boolean>().apply { value = isLoadingByDefault }
}