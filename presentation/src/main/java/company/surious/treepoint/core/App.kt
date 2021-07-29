package company.surious.treepoint.core

import android.app.Application
import company.surious.data.di.DataModules
import company.surious.domain.di.DomainModules
import company.surious.domain.logging.CrashlyticsLoggingTree
import company.surious.treepoint.di.PresentationModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                *DataModules.ALL,
                *DomainModules.ALL,
                *PresentationModules.ALL
            )
        }
        Timber.plant(Timber.DebugTree(), CrashlyticsLoggingTree)
    }
}