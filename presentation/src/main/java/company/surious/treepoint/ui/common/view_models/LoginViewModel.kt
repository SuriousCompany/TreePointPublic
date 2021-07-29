package company.surious.treepoint.ui.common.view_models

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import company.surious.domain.entities.LoggedInUser
import company.surious.domain.logging.logFlow
import company.surious.domain.use_case.GetCurrentUserUseCase
import company.surious.domain.use_case.GetLoginIntentUseCase
import company.surious.domain.use_case.LoginUseCase
import company.surious.treepoint.ui.common.models.navigation.MainNavigationDirection
import company.surious.treepoint.ui.common.models.navigation.RegistrationNavigationDirection
import company.surious.treepoint.ui.common.view_models.base.NavigationViewModel

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val getLoginIntentUseCase: GetLoginIntentUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : NavigationViewModel() {

    var isLoading = MutableLiveData<Boolean>().apply { value = false }

    val signInIntent by lazy {
        getLoginIntentUseCase.get()
    }

    fun login(intent: Intent) {
        isLoading.value = true
        val flow = "Login with Google"
        logFlow(flow)
        disposables.add(
            loginUseCase.execute(intent).subscribe(::onUserSignedIn) { error ->
                isLoading.value = false
                handleError(flow, error)
            }
        )
    }

    private fun onUserSignedIn(loggedInUser: LoggedInUser) {
        val flow = "User ${loggedInUser.email} signed in. Request current user."
        logFlow(flow)
        disposables.add(
            getCurrentUserUseCase.execute(null).subscribe(
                { registeredUser ->
                    //TODO check, is it blocked
                    isLoading.value = false
                    navigationSource.value = MainNavigationDirection(registeredUser)
                },
                { error ->
                    isLoading.value = false
                    handleError(flow, error)
                },
                {
                    isLoading.value = false
                    navigationSource.value = RegistrationNavigationDirection(loggedInUser)
                })
        )
    }
}