package company.surious.domain.use_case.auth

import company.surious.domain.managers.LoginManager

class GetLoginIntentUseCase(private val loginManager: LoginManager) {
    fun get() = loginManager.signInIntent
}