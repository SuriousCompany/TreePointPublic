package company.surious.treepoint.ui.common.view_models.base

import androidx.lifecycle.MutableLiveData
import company.surious.domain.logging.logUnhandledError
import company.surious.treepoint.ui.common.models.navigation.ErrorNavigationDirection
import company.surious.treepoint.ui.common.models.navigation.NavigationDirection

open class NavigationViewModel : DisposableViewModel() {
    var navigationSource = MutableLiveData<NavigationDirection>()

    fun handleError(flow: String, error: Throwable) {
        logUnhandledError(error, flow)
        navigationSource.value = ErrorNavigationDirection(error)
    }

}