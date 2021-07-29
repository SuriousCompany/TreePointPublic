package company.surious.data.mappers

import com.google.firebase.auth.FirebaseUser
import company.surious.domain.entities.LoggedInUser

object LoggedInUserMapper {
    /**
     * Firebase user should be checked. Email cannot be null.
     * @param user
     * @return mapped user
     */
    fun map(user: FirebaseUser) = LoggedInUser(user.uid, user.email!!)
}
