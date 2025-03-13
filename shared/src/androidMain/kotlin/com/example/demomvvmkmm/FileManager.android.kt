package com.example.demomvvmkmm

import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

actual class FileManager {
    actual fun fun1() {
        val workRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInitialDelay(3, TimeUnit.SECONDS) // Trì hoãn công việc 3 giây
            .build()

        // Enqueue công việc vào WorkManager
        WorkManager.getInstance(MyApplication.getAppContext())
            .enqueue(workRequest)
    }

    actual fun readFileFromDevice() {

    }
}