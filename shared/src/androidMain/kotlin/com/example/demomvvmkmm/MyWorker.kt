package com.example.demomvvmkmm

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        // Thực hiện tác vụ nền, ví dụ: tải dữ liệu, đồng bộ hóa
        try {
            // Giả lập tác vụ nền (3 giây)
            Thread.sleep(3000)
            Log.d("MyWorker", "Task completed after 3 seconds delay")
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}