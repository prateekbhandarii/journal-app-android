package com.pb.audia.app.di

import com.pb.audia.app.MyApplication
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {

    single<CoroutineScope> {
        (androidApplication() as MyApplication).applicationScope
    }
}