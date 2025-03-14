package com.example.demomvvmkmm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

//actual class MyViewModel(
//    private val userRepository: MyRepositoryImpl
//) : ViewModel() {
//    private val _user = MutableStateFlow<User?>(null)
//    val user: StateFlow<User?> = _user
//    actual fun loadUser() {
//        _user.value = userRepository.getUser()
//    }
//}