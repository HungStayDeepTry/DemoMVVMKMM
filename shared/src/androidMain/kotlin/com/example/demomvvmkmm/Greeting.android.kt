package com.example.demomvvmkmm

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI


@OptIn(KoinExperimentalAPI::class)
@Composable
actual fun MyScreen() {
    val myVM = koinViewModel<MyViewModel>()
    val username by myVM.user.collectAsState()
    LaunchedEffect(
        username
    ) {
        myVM.loadUser()
    }
    Box(modifier = Modifier.fillMaxSize()){
        Text(username.toString())
    }
}