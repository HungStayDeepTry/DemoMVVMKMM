package com.example.demomvvmkmm

import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface MyRepository {
    fun helloWorld(): String
}
class MyRepositoryImpl(
    private val fileManager: FileManager
): MyRepository {
    private val _fileUriFlow = MutableSharedFlow<String?>(replay = 1)  // 🔹 StateFlow để ViewModel quan sát
    val fileUriFlow = _fileUriFlow.asSharedFlow()
    init{
        observeFileUriEvent()
    }
    fun getUser(): User {
        // Ví dụ logic lấy dữ liệu người dùng từ cơ sở dữ liệu hoặc API
        fileManager.fun1()
        return User("John Doe", 30)
    }

    override fun helloWorld(): String {
        return "Hola"
    }
    suspend fun shareFile(){
        fileManager.shareFile()
    }
    private fun observeFileUriEvent() {
        // Lắng nghe fileUriEvent trong một coroutine
        CoroutineScope(Dispatchers.IO).launch {
//            fileManager.
//            fileManager.fileUriEvent.collect { intent ->
//                // Khi có sự thay đổi, xử lý Intent
//                intent?.let {
//                    val fileUri = it.getParcelableExtra<Uri>("fileUri")
//                    // Chẳng hạn, bạn có thể xử lý URI này theo ý muốn
//                    println("File shared with URI: $fileUri")
//                    // Có thể gửi URI này vào UI hoặc ViewModel
//                }
//            }
            fileManager.filePathEvent.collectLatest { uriString ->
                rsRepoShareState()
                uriString?.let {
                    if (!uriString.isNullOrEmpty()) { // 🔥 Kiểm tra tránh null
                        _fileUriFlow.emit(uriString)
                    }
                }
            }
        }
    }
    suspend fun rsRepoShareState(){
        _fileUriFlow.emit(null)
    }
}
data class User(val name: String, val age: Int)