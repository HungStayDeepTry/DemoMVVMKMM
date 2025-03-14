package com.example.demomvvmkmm

import android.content.Context
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule = module {
    single { FileManager(get()) }
//    single {
//        MyRepositoryImpl(get())
//    }
//    single{
//        MyViewModel(get())
//    }
}