package company.surious.data.firestore

import com.google.firebase.firestore.FirebaseFirestore
import company.surious.data.extensions.getAsync
import company.surious.data.extensions.setAsync
import company.surious.domain.errors.TreeError.CommunicationError.DetailedFirestoreError
import company.surious.domain.errors.TreeError.LogicError.NotUniqueUsernameError
import company.surious.domain.extensions.mapErrors
import company.surious.domain.repositories.UserNameRepository
import io.reactivex.rxjava3.core.Completable

class FirestoreUserNameRepository(private val firebaseFirestore: FirebaseFirestore) :
    UserNameRepository {

    private val userNamesCollectionName by lazy { FirestoreContract.Collections.USER_NAMES }

    override fun checkUserName(userName: String): Completable =
        firebaseFirestore
            .collection(userNamesCollectionName)
            .document(userName)
            .getAsync()
            .mapErrors {
                DetailedFirestoreError(it, userNamesCollectionName, userName)
            }
            .flatMapCompletable {
                if (it.exists()) {
                    Completable.error(NotUniqueUsernameError(userName))
                } else {
                    Completable.complete()
                }
            }


    override fun reserveUserName(userName: String): Completable =
        firebaseFirestore
            .collection(userNamesCollectionName)
            .document(userName)
            .setAsync(mapOf(Pair(userName, userName)))
            .mapErrors { DetailedFirestoreError(it, userNamesCollectionName, userName) }
}