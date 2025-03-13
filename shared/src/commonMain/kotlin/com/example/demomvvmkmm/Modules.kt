package com.example.demomvvmkmm

import com.example.demomvvmkmm.FileManager
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single {
        MyRepositoryImpl(get())
    }.bind<MyRepository>()
    viewModelOf(::MyViewModel)
}