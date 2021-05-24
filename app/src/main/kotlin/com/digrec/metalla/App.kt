package com.digrec.metalla

import android.app.Application
import com.digrec.metalla.di.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Created by Dejan Igrec
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                Modules.viewModelModule,
            )
        }

        Timber.plant(Timber.DebugTree())

        Timber.i("=== Metalla v${BuildConfig.VERSION_NAME} ===")
    }
}
