package company.surious.data.managers

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import company.surious.data.extensions.signOutAsync
import company.surious.data.mappers.LoggedInUserMapper
import company.surious.domain.entities.LoggedInUser
import company.surious.domain.errors.LoginError
import company.surious.domain.extensions.mapErrors
import company.surious.domain.extensions.safeOnError
import company.surious.domain.extensions.safeOnSuccess
import company.surious.domain.managers.LoginManager
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleEmitter


class GoogleLoginManager(
    private val signInClient: GoogleSignInClient,
    private val firebaseAuth: FirebaseAuth
) : LoginManager {

    override val signInIntent: Intent
        get() = signInClient.signInIntent

    override val currentUser: LoggedInUser?
        get() = firebaseAuth.currentUser?.let { LoggedInUserMapper.map(it) }

    override fun login(intent: Intent) =
        Single.create<LoggedInUser> { emitter ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    signInWithAccount(account, emitter)
                } else {
                    emitter.safeOnError(LoginError(customMessage = "Signed account is null"))
                }
            } catch (e: ApiException) {
                emitter.safeOnError(LoginError(e, "Api exception"))
            }
        }

    override fun logout(): Completable =
        Completable
            .fromAction { firebaseAuth.signOut() }
            .andThen(signInClient.signOutAsync())
            .mapErrors { LoginError(it, "Sign out error") }

    private fun signInWithAccount(
        account: GoogleSignInAccount,
        emitter: SingleEmitter<LoggedInUser>
    ) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { response ->
                if (response.isSuccessful) {
                    signInWithExistingUser(emitter)
                } else {
                    emitter.safeOnError(
                        LoginError(response.exception, "Response is not successful")
                    )
                }
            }
            .addOnFailureListener {
                emitter.safeOnError(LoginError(it, "Failure"))
            }
    }

    private fun signInWithExistingUser(emitter: SingleEmitter<LoggedInUser>) {
        val user = firebaseAuth.currentUser
        if (user != null) {
            val email = user.email
            if (email != null) {
                emitter.safeOnSuccess(LoggedInUserMapper.map(user))
            } else {
                emitter.safeOnError(
                    LoginError(customMessage = "User email is null")
                )
            }
        } else {
            emitter.safeOnError(
                LoginError(customMessage = "Current Firebase user is null")
            )
        }
    }
}