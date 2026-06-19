package com.pb.audia.app

import android.app.Application
import com.pb.audia.BuildConfig
import com.pb.audia.app.di.appModule
import com.pb.audia.memo.di.memoModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : Application() {

    val applicationScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@MyApplication)
            modules(
                appModule,
                memoModule
            )
        }
    }

}