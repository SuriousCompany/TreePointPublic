package company.surious.domain.entities.users

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoggedInUser(val id: String, val email: String) : Parcelable