package com.example.examenfinal5.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.examenfinal5.dao.GatoDao
import com.example.examenfinal5.entity.GatoEntity

@Database(entities = [GatoEntity::class], version = 1)
abstract class GatoDatabase : RoomDatabase() {
    abstract fun gatoDao(): GatoDao
}