package company.surious.domain.use_case

import android.content.Intent
import company.surious.domain.entities.LoggedInUser
import company.surious.domain.managers.LoginManager
import company.surious.domain.preferences.InnerPreferences
import company.surious.domain.use_case.base.SingleUseCase
import io.reactivex.Single

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