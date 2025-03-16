package com.example.demomvvmkmm

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

expect class FileManager{
     val filePathEvent: SharedFlow<String?>
     fun fun1()
     fun readFileFromDevice()
     fun createFile(path: String): String
     suspend fun shareFile()
}