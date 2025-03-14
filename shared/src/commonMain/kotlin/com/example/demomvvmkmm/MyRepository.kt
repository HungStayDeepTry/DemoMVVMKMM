package com.example.demomvvmkmm

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

interface MyRepository {
    fun helloWorld(): String
}
class MyRepositoryImpl(
    private val fileManager: FileManager
): MyRepository {
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
    fun shareFile(){
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
        }
    }

}
data class User(val name: String, val age: Int)