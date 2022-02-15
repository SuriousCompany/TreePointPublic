package company.surious.data.firestore

import com.google.firebase.firestore.FirebaseFirestore
import company.surious.data.extensions.getAsyncMaybe
import company.surious.data.extensions.setAsync
import company.surious.domain.entities.users.CreditsAddedEvent
import company.surious.domain.entities.users.RegisteredUser
import company.surious.domain.errors.TreeError.CommunicationError.DetailedFirestoreError
import company.surious.domain.extensions.mapErrors
import company.surious.domain.extensions.userOrError
import company.surious.domain.repositories.CurrentUserRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class FirestoreCurrentUserRepository(
    private val firestore: FirebaseFirestore
) : CurrentUserRepository {
    private val usersCollectionName by lazy { FirestoreContract.Collections.USERS }

    override fun updateCurrentUser(user: RegisteredUser): Completable =
        firestore
            .collection(usersCollectionName)
            .document(user.id)
            .setAsync(user)
            .mapErrors {
                DetailedFirestoreError(it, usersCollectionName, user.id)
            }

    override fun getCurrentUser(userId: String): Maybe<RegisteredUser> =
        firestore
            .collection(usersCollectionName)
            .document(userId)
            .getAsyncMaybe(RegisteredUser::class)
            .mapErrors {
                DetailedFirestoreError(it, usersCollectionName, userId)
            }

    override fun addCredits(currentUserId: String, credits: Double): Single<CreditsAddedEvent> =
        getCurrentUser(currentUserId).subscribeOn(Schedulers.io())
            .userOrError(currentUserId)
            .flatMap { user ->
                user.credits += credits
                updateCurrentUser(user).subscribeOn(Schedulers.io()).toSingle {
                    CreditsAddedEvent(credits, user.credits)
                }
            }

    override fun decrementCredits(currentUserId: String): Completable =
        getCurrentUser(currentUserId).subscribeOn(Schedulers.io())
            .userOrError(currentUserId)
            .flatMapCompletable { user ->
                user.credits--
                if (user.credits < 0) {
                    user.credits = 0.0
                }
                updateCurrentUser(user).subscribeOn(Schedulers.io())
            }

    override fun decrementCredits(currentUser: RegisteredUser): Completable =
        currentUser.run {
            credits--
            if (credits < 0) {
                credits = 0.0
            }
            updateCurrentUser(this)
        }


    override fun observeCurrentUser(userId: String): Observable<RegisteredUser> {
        TODO()
    }
}