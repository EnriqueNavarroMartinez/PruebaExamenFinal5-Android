package com.example.examenfinal5.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "gatos")
data class GatoEntity ( //importante que sea parentesis y no {

    @PrimaryKey(true) val id : Int = 0,
    @ColumnInfo(name = "name" ) val name:String?,
    @ColumnInfo(name = "temperament" ) val temperament:String?,
    @ColumnInfo(name = "origin" )val origin:String,
    @ColumnInfo(name = "description" ) val description: String

)