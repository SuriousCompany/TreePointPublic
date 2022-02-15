package company.surious.domain.use_case.tree_point

import company.surious.domain.entities.identification.result.details.PlantDetails
import company.surious.domain.entities.plants.TreePoint
import company.surious.domain.entities.plants.TreePointDraft
import company.surious.domain.entities.users.RegisteredUser
import company.surious.domain.extensions.userOrError
import company.surious.domain.preferences.InnerPreferences
import company.surious.domain.repositories.CurrentUserRepository
import company.surious.domain.repositories.TreePointRepository
import company.surious.domain.use_case.base.SingleUseCase
import company.surious.domain.use_case.delegates.checkers.CurrentUserChecker
import io.reactivex.rxjava3.core.Single

class CreateTreePointUseCase(
    private val currentUserRepository: CurrentUserRepository,
    private val preferences: InnerPreferences,
    private val treePointRepository: TreePointRepository
) : SingleUseCase<TreePointDraft, String>() {

    override fun createSingle(params: TreePointDraft): Single<String> {
        val currentUserId = preferences.currentUserId
        return CurrentUserChecker.idOrErrorSingle(currentUserId) { id ->
            currentUserRepository.getCurrentUser(id)
                .userOrError(id)
                .flatMap { user ->
                    treePointRepository.createTreePoint(createTreePoint(user, params))
                }
        }
    }

    private fun createTreePoint(registeredUser: RegisteredUser, treePointDraft: TreePointDraft) =
        with(treePointDraft) {
            val creationTime = System.currentTimeMillis()
            TreePoint(
                "",
                lat,
                lng,
                registeredUser.userName,
                registeredUser.id,
                ripeStartMonth,
                ripeEndMonth,
                creatorComment,
                PlantDetails(),
                false,
                null,
                creationTime,
                creationTime,
                -1
            )
        }
}