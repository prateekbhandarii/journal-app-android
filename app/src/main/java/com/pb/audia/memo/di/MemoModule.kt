package com.pb.audia.memo.di

import com.pb.audia.memo.data.recording.AndroidVoiceRecorder
import com.pb.audia.memo.domain.recording.VoiceRecorder
import com.pb.audia.memo.presentation.list_screen.MemoListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val memoModule = module {

    single {
        AndroidVoiceRecorder(
            context = androidApplication(),
            applicationScope = get()
        )
    } bind VoiceRecorder::class

    viewModelOf(::MemoListViewModel)
}