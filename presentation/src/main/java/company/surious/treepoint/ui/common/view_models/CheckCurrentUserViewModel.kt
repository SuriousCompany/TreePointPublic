package company.surious.treepoint.ui.common.view_models

import androidx.lifecycle.MutableLiveData
import company.surious.domain.entities.RegisteredUser
import company.surious.domain.logging.logFlow
import company.surious.domain.preferences.InnerPreferences
import company.surious.domain.use_case.auth.GetCurrentUserUseCase
import company.surious.domain.use_case.auth.GetLoggedInUserUseCase
import company.surious.treepoint.ui.common.models.navigation.LoginNavigationDirection
import company.surious.treepoint.ui.common.models.navigation.MainNavigationDirection
import company.surious.treepoint.ui.common.models.navigation.RegistrationNavigationDirection
import company.surious.treepoint.ui.common.view_models.base.NavigationViewModel

class CheckCurrentUserViewModel(
    private val innerPreferences: InnerPreferences,
    private val getLoggedInUserUseCase: GetLoggedInUserUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : NavigationViewModel() {

    val loadingState = MutableLiveData<Boolean>().apply { value = true }

    fun checkUser(registeredUser: RegisteredUser?) {
        var flow = "Check registered user"
        logFlow(flow)
        if (innerPreferences.currentUserId == null) {
            flow = "No saved user id"
            logFlow(flow)
            loadingState.value = false
            navigationSource.value = LoginNavigationDirection
        } else {
            if (registeredUser == null) {
                requestUser()
            } else {
                flow = "Started screen with registered user: ${registeredUser.email}"
                logFlow(flow)
                loadingState.value = false
                navigationSource.value = MainNavigationDirection(registeredUser)
            }
        }
    }

    private fun requestUser() {
        var flow = "Request current user"
        logFlow(flow)
        disposables.add(
            getCurrentUserUseCase.execute(null).subscribe(
                { receivedUser ->
                    flow = "Registered user: ${receivedUser.email}"
                    logFlow(flow)
                    //TODO check, is it blocked
                    loadingState.value = false
                    navigationSource.value = MainNavigationDirection(receivedUser)
                },
                { error ->
                    handleError(flow, error)
                },
                {
                    flow = "There is no registered user"
                    logFlow(flow)
                    val loggedInUser = getLoggedInUserUseCase.get()
                    val direction = if (loggedInUser != null) {
                        flow = "There is logged in user: ${loggedInUser.email}"
                        logFlow(flow)
                        RegistrationNavigationDirection(loggedInUser)
                    } else {
                        flow = "There is no logged user"
                        logFlow(flow)
                        LoginNavigationDirection
                    }
                    navigationSource.value = direction
                })
        )
    }
}