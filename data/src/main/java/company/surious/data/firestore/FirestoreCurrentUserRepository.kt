package company.surious.data.firestore

import com.google.firebase.firestore.FirebaseFirestore
import company.surious.data.extensions.getAsyncMaybe
import company.surious.data.extensions.setAsync
import company.surious.domain.entities.RegisteredUser
import company.surious.domain.errors.DetailedFirestoreError
import company.surious.domain.extensions.mapErrors
import company.surious.domain.repositories.CurrentUserRepository
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable

//TODO use SimpleCollectionRepository
class FirestoreCurrentUserRepository(
    private val firestore: FirebaseFirestore
) : CurrentUserRepository {
    private val usersCollectionName by lazy { FirestoreContract.Collections.USERS }

    override fun updateCurrentUser(user: RegisteredUser): Completable =
        firestore
            .collection(usersCollectionName)
            .document(user.id)
            .setAsync(user)
            .mapErrors { DetailedFirestoreError(it, usersCollectionName, user.id) }

    override fun getCurrentUser(userId: String): Maybe<RegisteredUser> =
        firestore
            .collection(usersCollectionName)
            .document(userId)
            .getAsyncMaybe(RegisteredUser::class)
            .mapErrors { DetailedFirestoreError(it, usersCollectionName, userId) }

    override fun observeCurrentUser(userId: String): Observable<RegisteredUser> {
        TODO()
    }
}