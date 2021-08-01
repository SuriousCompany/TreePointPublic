package company.surious.domain.use_case.auth

import company.surious.domain.managers.LoginManager

class GetLoggedInUserUseCase(private val loginManager: LoginManager) {
    fun get() = loginManager.currentUser
}