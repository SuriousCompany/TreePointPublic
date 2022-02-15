package company.surious.treepoint.ui.common.view_models

import androidx.lifecycle.MutableLiveData
import company.surious.domain.errors.TreeError.LogicError.NotUniqueUsernameError
import company.surious.domain.logging.logFlow
import company.surious.domain.use_case.auth.RegistrationUseCase
import company.surious.domain.validators.UsernameValidator
import company.surious.treepoint.ui.common.models.navigation.MainNavigationDirection
import company.surious.treepoint.ui.common.providers.TextResourcesProvider
import company.surious.treepoint.ui.common.view_models.base.NavigationViewModel

class RegistrationViewModel(
    private val registrationUseCase: RegistrationUseCase,
    private val textResourcesProvider: TextResourcesProvider
) : NavigationViewModel() {

    val userName = MutableLiveData<String>().apply { value = "" }
    val isLoading = MutableLiveData<Boolean>().apply { value = false }
    val errorText = MutableLiveData<String>().apply { value = "" }
    
    fun register() {
        isLoading.value = true
        val username = userName.value!!
        var flow = "Register: \"$username\""
        logFlow(flow)
        if (UsernameValidator.isValid(username)) {
            flow = "Username is valid"
            performRegistration(username)
        } else {
            flow = "Username is not valid"
            isLoading.value = false
            errorText.value = textResourcesProvider.getUsernameInvalidText()
        }
        logFlow(flow)
    }

    private fun performRegistration(validUsername: String) {
        var flow = "Perform registration: \"$validUsername\""
        disposables.add(
            registrationUseCase.execute(validUsername).subscribe(
                { registeredUser ->
                    isLoading.value = false
                    flow = "User \"$validUsername\" registered"
                    logFlow(flow)
                    navigationSource.value = MainNavigationDirection(registeredUser)
                },
                { error ->
                    isLoading.value = false
                    if (error is NotUniqueUsernameError) {
                        errorText.value = textResourcesProvider.getUsernameExistsText()
                    } else {
                        handleError(flow, error)
                    }
                }
            )
        )
    }
}