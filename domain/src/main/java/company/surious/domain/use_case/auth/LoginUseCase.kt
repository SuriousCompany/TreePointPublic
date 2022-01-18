package company.surious.domain.use_case.auth

import android.content.Intent
import company.surious.domain.entities.users.LoggedInUser
import company.surious.domain.managers.LoginManager
import company.surious.domain.preferences.InnerPreferences
import company.surious.domain.use_case.base.SingleUseCase
import io.reactivex.rxjava3.core.Single

class LoginUseCase(
    private val loginManager: LoginManager,
    private val preferences: InnerPreferences
) : SingleUseCase<Intent, LoggedInUser>() {

    override fun createSingle(params: Intent): Single<LoggedInUser> =
        loginManager.login(params).map {
            preferences.currentUserId = it.id
            it
        }
}