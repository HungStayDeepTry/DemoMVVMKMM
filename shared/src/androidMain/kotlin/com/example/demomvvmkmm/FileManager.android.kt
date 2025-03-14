package com.example.demomvvmkmm

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.util.concurrent.TimeUnit

 class FileManager(
    private val context: Context
) {
    private val _fileUriEvent = MutableStateFlow<Intent?>(null)
    val fileUriEvent: StateFlow<Intent?> get() = _fileUriEvent
    actual fun fun1() {
        // Tạo file trước
        val filePath = createFile()

        // Truyền đường dẫn file vào WorkManager
        val workRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInitialDelay(2, TimeUnit.MINUTES) // Xóa file sau 2 phút
            .setInputData(workDataOf("filePath" to filePath)) // Truyền filePath vào Worker
            .build()

        // Thêm vào hàng đợi của WorkManager
        WorkManager.getInstance(context).enqueue(workRequest)
    }

    actual fun readFileFromDevice() {

    }
    actual fun createFile() : String{
        val file = File(context.filesDir, "ten123")
        file.writeText("Đây là nội dung của file.") // Ghi nội dung vào file
        Log.d("FileContent", "createFile: ${file.absolutePath}")
        return file.absolutePath
    }
    actual fun shareFile(){
        val filePath = createFile()  // Tạo file
        val file = File(filePath)

        // Kiểm tra xem tệp có tồn tại không
        if (file.exists()) {
            // Tạo URI an toàn từ FileProvider
            val fileUri: Uri = FileProvider.getUriForFile(
                context,
                "com.example.demomvvmkmm.fileprovider",  // authority của bạn
                file
            )

            // Tạo một Intent để chia sẻ tệp
            val shareIntent = Intent("com.example.demomvvmkmm.SHARE_FILE").apply {
                putExtra("fileUri", fileUri)
            }
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            _fileUriEvent.update {
                shareIntent
            }
//            LocalBroadcastManager.getInstance(context).sendBroadcast(shareIntent)
//            context.applicationContext.sendBroadcast(shareIntent)
        } else {
            Log.e("FileManager", "Tệp không tồn tại!")
        }
    }
}