package company.surious.domain.use_case.auth

import company.surious.domain.entities.users.RegisteredUser
import company.surious.domain.preferences.InnerPreferences
import company.surious.domain.repositories.CurrentUserRepository
import company.surious.domain.use_case.base.MaybeUseCase
import company.surious.domain.use_case.delegates.checkers.CurrentUserChecker
import io.reactivex.rxjava3.core.Maybe

class GetCurrentUserUseCase(
    private val currentUserRepository: CurrentUserRepository,
    private val innerPreferences: InnerPreferences
) : MaybeUseCase<Void?, RegisteredUser>() {

    override fun createMaybe(params: Void?): Maybe<RegisteredUser> {
        val id = innerPreferences.currentUserId
        return CurrentUserChecker.idOrErrorMaybe(id) { currentUserId ->
            currentUserRepository.getCurrentUser(currentUserId)
        }
    }
}