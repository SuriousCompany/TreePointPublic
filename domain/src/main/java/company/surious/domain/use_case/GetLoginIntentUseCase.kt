package company.surious.domain.use_case

import company.surious.domain.managers.LoginManager

class GetLoginIntentUseCase(private val loginManager: LoginManager) {
    fun get() = loginManager.signInIntent
}