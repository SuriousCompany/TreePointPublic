package company.surious.domain.use_case.auth

import company.surious.domain.entities.users.RegisteredUser
import company.surious.domain.errors.PreferencesError
import company.surious.domain.preferences.InnerPreferences
import company.surious.domain.repositories.CurrentUserRepository
import company.surious.domain.use_case.base.MaybeUseCase
import io.reactivex.rxjava3.core.Maybe

class GetCurrentUserUseCase(
    private val currentUserRepository: CurrentUserRepository,
    private val innerPreferences: InnerPreferences
) : MaybeUseCase<Void?, RegisteredUser>() {

    override fun createMaybe(params: Void?): Maybe<RegisteredUser> =
        let {
            val id = innerPreferences.currentUserId
            if (id != null) {
                currentUserRepository.getCurrentUser(id)
            } else {
                Maybe.error(PreferencesError(customMessage = "User id is not initialized"))
            }
        }

}