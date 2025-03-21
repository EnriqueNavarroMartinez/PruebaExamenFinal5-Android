package com.example.examenfinal5.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.examenfinal5.entity.GatoEntity

@Dao
interface GatoDao {

    @Insert
    fun insertAll(gatos: List<GatoEntity>)

    @Delete
    fun delete(gato: GatoEntity)

    @Query("SELECT * FROM gatos")
    fun getAllGatos(): List<GatoEntity>
}