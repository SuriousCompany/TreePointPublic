package company.surious.domain.use_case.auth

import company.surious.domain.entities.RegisteredUser
import company.surious.domain.managers.LoginManager
import company.surious.domain.repositories.CurrentUserRepository
import company.surious.domain.repositories.UserNameRepository
import company.surious.domain.use_case.base.SingleUseCase
import io.reactivex.Single

class RegistrationUseCase(
    private val currentUserRepository: CurrentUserRepository,
    private val userNameRepository: UserNameRepository,
    private val loginManager: LoginManager
) : SingleUseCase<String, RegisteredUser>() {

    override fun createSingle(params: String): Single<RegisteredUser> {
        val registeredUser = createRegisteredUser(params)
        return userNameRepository.checkUserName(params)
            .andThen(userNameRepository.reserveUserName(params))
            .andThen(currentUserRepository.updateCurrentUser(registeredUser))
            .toSingle { registeredUser }
    }


    private fun createRegisteredUser(username: String) =
        with(loginManager.currentUser!!) {
            RegisteredUser(id, email, username)
        }
}