package com.example.demomvvmkmm

import androidx.room.Room

//actual class DatabaseDriverFactory {
//    actual fun createDriver(): Any {
//        val database = Room.databaseBuilder(
//            context,
//            MyDatabase::class.java,
//            "my_database.db"
//        ).build()
//
//        // Room không sử dụng SqlDriver, nhưng trả về RoomDatabase
//        return database
//    }
//}