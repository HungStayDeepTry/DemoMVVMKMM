package com.example.demomvvmkmm

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.File

class MyWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val filePath = inputData.getString("filePath") ?: return Result.failure()
        val file = File(filePath)

        return when {
            file.exists() && file.delete() -> {
                Log.d("MyWorker", "File deleted successfully: $filePath")
                Result.success()
            }
            !file.exists() -> {
                Log.d("MyWorker", "File does not exist, nothing to delete: $filePath")
                Result.success()
            }
            else -> {
                Log.e("MyWorker", "Failed to delete file: $filePath")
                Result.failure()
            }
        }
    }
}