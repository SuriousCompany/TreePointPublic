package company.surious.domain.entities.users

import android.os.Parcelable
import company.surious.domain.rules.CreditRules
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisteredUser(
    var id: String = "",
    var email: String = "",
    var userName: String = "",
    var hasAccess: Boolean = true,
    var credits: Double = CreditRules.INITIAL_CREDITS
) : Parcelable