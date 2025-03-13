package com.example.demomvvmkmm

import androidx.compose.runtime.Composable

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}
@Composable
expect fun MyScreen()