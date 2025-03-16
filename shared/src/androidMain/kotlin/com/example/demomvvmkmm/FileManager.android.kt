package com.example.demomvvmkmm

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
//import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.util.concurrent.TimeUnit

actual class FileManager(
    private val context: Context
) {
    private val _fileUriEvent = MutableSharedFlow<String?>(replay = 1)
    actual val filePathEvent = _fileUriEvent.asSharedFlow()
    actual fun fun1() {
        // Tạo file trước, demo tạo ở documents
        val filePath = createFile("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)}/MyAppFolder")

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
    actual fun createFile(path: String) : String{
        val folder = File(path)

        return (if (!folder.exists()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                // Android 11+ cần quyền MANAGE_EXTERNAL_STORAGE
                if (Environment.isExternalStorageManager()) {
                    if (folder.mkdirs()) path else null
                } else {
                    Log.e("FileManager", "Chưa có quyền MANAGE_EXTERNAL_STORAGE")
                    null
                }
            } else {
                if (folder.mkdirs()) path else null
            }
        } else {
            Log.d("FileManager", "Thư mục đã tồn tại: $path")
            path
        }).toString()
    }
    actual suspend fun shareFile(){
        val filePath = createFile("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)}/MyAppFolder")  // Tạo file
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
//            val shareIntent = Intent("com.example.demomvvmkmm.SHARE_FILE").apply {
//                putExtra("fileUri", fileUri)
//            }
//            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            rsOpenFileStatus()
            _fileUriEvent.emit(fileUri.toString())
//            LocalBroadcastManager.getInstance(context).sendBroadcast(shareIntent)
//            context.applicationContext.sendBroadcast(shareIntent)
        } else {
            Log.e("FileManager", "Tệp không tồn tại!")
        }
    }
    suspend fun rsOpenFileStatus(){
        _fileUriEvent.emit(null)
    }

}