package company.surious.domain.repositories

import company.surious.domain.entities.RegisteredUser
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable

interface CurrentUserRepository {

    fun updateCurrentUser(user: RegisteredUser): Completable

    fun getCurrentUser(userId: String): Maybe<RegisteredUser>

    fun observeCurrentUser(userId: String): Observable<RegisteredUser>
}