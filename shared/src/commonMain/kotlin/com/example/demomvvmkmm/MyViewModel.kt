package com.example.demomvvmkmm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MyViewModel(private val userRepository: MyRepositoryImpl): ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user
    init{
        observeFileUriEvent()
    }

    private val _fileUriFlow = MutableSharedFlow<String?>(replay = 1)  // 🔹 StateFlow để ViewModel quan sát
    val fileUriFlow: SharedFlow<String?> = _fileUriFlow
    fun loadUser() {
        // Giả sử bạn gọi repository để lấy dữ liệu người dùng
        _user.value = userRepository.getUser()
    }
    fun shareFile(){
        viewModelScope.launch{
            userRepository.shareFile()
        }
    }
    private fun observeFileUriEvent() {
        // Lắng nghe fileUriEvent trong một coroutine
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.fileUriFlow.collectLatest { uriString ->
                rsOpenFileStatus()
                uriString?.let {
                    if (!uriString.isNullOrEmpty()) { // 🔥 Kiểm tra tránh null
                        _fileUriFlow.emit(uriString)
                    }
                }
            }
        }
    }
    suspend fun rsOpenFileStatus(){
        _fileUriFlow.emit(null)
    }
}