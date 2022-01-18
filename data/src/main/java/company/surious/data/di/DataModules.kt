package company.surious.data.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import company.surious.data.config.DataConfig
import company.surious.data.firestore.*
import company.surious.data.managers.GoogleLoginManager
import company.surious.data.preferences.TreeSharedPreferences
import company.surious.data.retrofit.HttpLogger
import company.surious.data.retrofit.IdentificationApi
import company.surious.data.retrofit.IdentificationAuthInterceptor
import company.surious.data.retrofit.RetrofitIdentificationRepository
import company.surious.data.storage.FirebaseStorageRepository
import company.surious.domain.managers.LoginManager
import company.surious.domain.preferences.InnerPreferences
import company.surious.domain.preferences.UserPreferences
import company.surious.domain.repositories.*
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.binds
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object DataModules {
    val FIREBASE = module {
        single { FirebaseFirestore.getInstance() }
        single { FirebaseStorage.getInstance() }
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
    val END_POINTS = module {
        single(named("identification_url")) { "https://api.plant.id/v2/" }
    }
    val RETROFIT = module {
        single {
            OkHttpClient.Builder()
                .addInterceptor(IdentificationAuthInterceptor)
                .addInterceptor(
                    HttpLoggingInterceptor(HttpLogger).apply {
                        setLevel(HttpLoggingInterceptor.Level.BASIC)
                        redactHeader(IdentificationAuthInterceptor.AUTH_HEADER)
                    }
                )
                .callTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                .build()
        }
        single {
            Retrofit.Builder()
                .baseUrl(get<String>(qualifier = named("identification_url")))
                .client(get())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
        }
        single<IdentificationApi> { get<Retrofit>().create(IdentificationApi::class.java) }
    }
    val REPOSITORIES = module {
        single<UserNameRepository> { FirestoreUserNameRepository(get()) }
        single<CurrentUserRepository> { FirestoreCurrentUserRepository(get()) }
        single<TreeTypeRepository> { FirestoreTreeTypeRepository(get()) }
        single<TreePointRepository> { FirestoreTreePointRepository(get()) }
        single<CloudStorageRepository> { FirebaseStorageRepository(get()) }
        single<IdentificationRepository> { RetrofitIdentificationRepository(get()) }
        single<PlantDetailsRepository> { FirestorePlantDetailsRepository(get()) }
    }
    val PREFERENCES = module {
        single<InnerPreferences> { TreeSharedPreferences(get()) }
        single { TreeSharedPreferences(get()) }.binds(
            arrayOf(InnerPreferences::class, UserPreferences::class)
        )
    }
    val ALL = arrayOf(AUTH, FIREBASE, REPOSITORIES, PREFERENCES, END_POINTS, RETROFIT)
}