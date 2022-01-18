package company.surious.domain.entities.users

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisteredUser(
    var id: String = "",
    var email: String = "",
    var userName: String = "",
    var hasAccess: Boolean = true
) : Parcelable