package com.example.demomvvmkmm

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    Column(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()){
        Text(username.toString(), color = MaterialTheme.colors.onBackground)
        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { myVM.shareFile() }
        ) {
            Text("Chia se file")
        }
    }
}