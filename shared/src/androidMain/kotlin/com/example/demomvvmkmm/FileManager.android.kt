package com.example.demomvvmkmm

import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.io.File
import java.util.concurrent.TimeUnit

actual class FileManager(
    private val context: Context
) {
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
}