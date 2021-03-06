package company.surious.domain.use_case.auth

import company.surious.domain.managers.LoginManager
import company.surious.domain.preferences.InnerPreferences
import company.surious.domain.use_case.base.CompletableUseCase
import io.reactivex.rxjava3.core.Completable

class LogoutUseCase(
    private val loginManager: LoginManager,
    private val innerPreferences: InnerPreferences
) : CompletableUseCase<Void?>() {
    override fun createCompletable(params: Void?): Completable =
        loginManager.logout()
            .andThen(Completable.fromAction { innerPreferences.currentUserId = null })
}