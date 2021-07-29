package company.surious.domain.managers

import android.content.Intent
import company.surious.domain.entities.LoggedInUser
import io.reactivex.Completable
import io.reactivex.Single

interface LoginManager {
    val signInIntent: Intent

    val currentUser: LoggedInUser?

    fun login(intent: Intent): Single<LoggedInUser>

    fun logout(): Completable
}