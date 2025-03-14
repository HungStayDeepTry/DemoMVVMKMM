package com.example.demomvvmkmm

import kotlinx.coroutines.flow.StateFlow

expect class FileManager{
     fun fun1()
     fun readFileFromDevice()
     fun createFile(): String
     fun shareFile()
}