package company.surious.domain.managers

import android.content.Intent
import company.surious.domain.entities.users.LoggedInUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface LoginManager {
    val signInIntent: Intent

    val currentUser: LoggedInUser?

    fun login(intent: Intent): Single<LoggedInUser>

    fun logout(): Completable
}