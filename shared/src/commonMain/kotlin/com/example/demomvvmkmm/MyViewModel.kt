package com.example.demomvvmkmm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyViewModel(private val userRepository: MyRepositoryImpl): ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user
    fun loadUser() {
        // Giả sử bạn gọi repository để lấy dữ liệu người dùng
        _user.value = userRepository.getUser()
    }
}