package company.surious.domain.repositories

import company.surious.domain.entities.users.RegisteredUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable

interface CurrentUserRepository {

    fun updateCurrentUser(user: RegisteredUser): Completable

    fun getCurrentUser(userId: String): Maybe<RegisteredUser>

    fun observeCurrentUser(userId: String): Observable<RegisteredUser>
}