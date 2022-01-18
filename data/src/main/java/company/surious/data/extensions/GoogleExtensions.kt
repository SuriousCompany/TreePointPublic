package company.surious.data.extensions

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import company.surious.domain.extensions.safeOnComplete
import company.surious.domain.extensions.safeOnError
import io.reactivex.rxjava3.core.Completable

fun GoogleSignInClient.signOutAsync() = Completable.create { emitter ->
    signOut()
        .addOnSuccessListener { emitter.safeOnComplete() }
        .addOnFailureListener { emitter.safeOnError(it) }
}