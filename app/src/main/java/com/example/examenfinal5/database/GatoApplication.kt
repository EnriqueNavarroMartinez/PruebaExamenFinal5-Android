package com.example.examenfinal5.database

import android.app.Application
import androidx.room.Room

class GatoApplication : Application() {

    companion object{
        lateinit var database: GatoDatabase
    }
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this,
            GatoDatabase::class.java,
            "UserDatabase")
            .build()
    }
}