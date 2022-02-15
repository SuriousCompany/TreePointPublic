package company.surious.domain.use_case.identification

import company.surious.domain.entities.identification.IdentificationRequest
import company.surious.domain.entities.identification.result.IdentificationResult
import company.surious.domain.errors.TreeError.LogicError.NoCreditsForIdentificationError
import company.surious.domain.extensions.userOrError
import company.surious.domain.preferences.InnerPreferences
import company.surious.domain.repositories.CurrentUserRepository
import company.surious.domain.repositories.IdentificationRepository
import company.surious.domain.use_case.base.SingleUseCase
import company.surious.domain.use_case.delegates.checkers.CurrentUserChecker.idOrErrorSingle
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class IdentifyUseCase(
    private val identificationRepository: IdentificationRepository,
    private val currentUserRepository: CurrentUserRepository,
    private val preferences: InnerPreferences
) : SingleUseCase<IdentificationRequest, IdentificationResult>() {

    override fun createSingle(params: IdentificationRequest): Single<IdentificationResult> =
        idOrErrorSingle(preferences.currentUserId) { currentUserId ->
            currentUserRepository.getCurrentUser(currentUserId)
                .userOrError(currentUserId)
                .flatMap { user ->
                    if (user.credits >= 1) {
                        currentUserRepository.decrementCredits(user).subscribeOn(Schedulers.io())
                            .andThen(identificationRepository.identify(params))
                    } else {
                        Single.error(NoCreditsForIdentificationError(user.credits))
                    }
                }
        }

}