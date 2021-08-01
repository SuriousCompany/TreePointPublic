package company.surious.data.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import company.surious.data.config.DataConfig
import company.surious.data.firestore.FirestoreCurrentUserRepository
import company.surious.data.firestore.FirestoreTreePointRepository
import company.surious.data.firestore.FirestoreTreeTypeRepository
import company.surious.data.firestore.FirestoreUserNameRepository
import company.surious.data.managers.GoogleLoginManager
import company.surious.data.preferences.TreeSharedPreferences
import company.surious.domain.managers.LoginManager
import company.surious.domain.preferences.InnerPreferences
import company.surious.domain.preferences.UserPreferences
import company.surious.domain.repositories.CurrentUserRepository
import company.surious.domain.repositories.TreePointRepository
import company.surious.domain.repositories.TreeTypeRepository
import company.surious.domain.repositories.UserNameRepository
import org.koin.dsl.binds
import org.koin.dsl.module

object DataModules {
    val FIREBASE = module {
        single { FirebaseFirestore.getInstance() }
    }
    val AUTH = module {
        single { FirebaseAuth.getInstance() }
        single<LoginManager> { GoogleLoginManager(get(), get()) }
        single<GoogleSignInClient> {
            val context: Context = get()
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(DataConfig.getWebClientId())
                .requestEmail()
                .build()
            GoogleSignIn.getClient(context, gso)
        }
    }
    val REPOSITORIES = module {
        single<UserNameRepository> { FirestoreUserNameRepository(get()) }
        single<CurrentUserRepository> { FirestoreCurrentUserRepository(get()) }
        single<TreeTypeRepository> { FirestoreTreeTypeRepository(get()) }
        single<TreePointRepository> { FirestoreTreePointRepository(get()) }
    }
    val PREFERENCES = module {
        single<InnerPreferences> { TreeSharedPreferences(get()) }
        single { TreeSharedPreferences(get()) }.binds(
            arrayOf(InnerPreferences::class, UserPreferences::class)
        )
    }
    val ALL = arrayOf(AUTH, FIREBASE, REPOSITORIES, PREFERENCES)
}