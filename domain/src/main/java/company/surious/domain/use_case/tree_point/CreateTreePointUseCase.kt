package company.surious.domain.use_case.tree_point

import company.surious.domain.entities.RegisteredUser
import company.surious.domain.entities.TreePoint
import company.surious.domain.entities.TreePointDraft
import company.surious.domain.errors.PreferencesError
import company.surious.domain.preferences.InnerPreferences
import company.surious.domain.repositories.CurrentUserRepository
import company.surious.domain.repositories.TreePointRepository
import company.surious.domain.use_case.base.CompletableUseCase
import io.reactivex.Completable

class CreateTreePointUseCase(
    private val currentUserRepository: CurrentUserRepository,
    private val preferences: InnerPreferences,
    private val treePointRepository: TreePointRepository
) : CompletableUseCase<TreePointDraft>() {

    override fun createCompletable(params: TreePointDraft): Completable {
        val currentUserId = preferences.currentUserId
        return if (currentUserId != null) {
            currentUserRepository.getCurrentUser(currentUserId).flatMapCompletable { user ->
                treePointRepository.updateTreePoint(createTreePoint(user, params))
            }
        } else {
            Completable.error(PreferencesError(customMessage = "User id is not initialized"))
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
                type,
                false,
                null,
                creationTime,
                creationTime,
                -1
            )
        }
}