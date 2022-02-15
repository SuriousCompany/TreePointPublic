package company.surious.domain.repositories

import company.surious.domain.entities.users.CreditsAddedEvent
import company.surious.domain.entities.users.RegisteredUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface CurrentUserRepository {

    fun updateCurrentUser(user: RegisteredUser): Completable

    fun getCurrentUser(userId: String): Maybe<RegisteredUser>

    fun observeCurrentUser(userId: String): Observable<RegisteredUser>

    fun addCredits(currentUserId: String, credits: Double): Single<CreditsAddedEvent>

    fun decrementCredits(currentUserId: String): Completable

    fun decrementCredits(currentUser: RegisteredUser): Completable
}