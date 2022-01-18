package company.surious.data.firestore.mappers

import com.google.firebase.auth.FirebaseUser
import company.surious.domain.entities.users.LoggedInUser

object FirestoreLoggedInUserMapper {
    /**
     * Firebase user should be checked. Email cannot be null.
     * @param user
     * @return mapped user
     */
    fun map(user: FirebaseUser) = LoggedInUser(user.uid, user.email!!)
}
