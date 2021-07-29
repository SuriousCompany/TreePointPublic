package company.surious.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisteredUser(
    var id: String = "",
    var email: String = "",
    var userName: String = "",
    var hasAccess: Boolean = true
) : Parcelable