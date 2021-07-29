package company.surious.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoggedInUser(val id: String, val email: String) : Parcelable