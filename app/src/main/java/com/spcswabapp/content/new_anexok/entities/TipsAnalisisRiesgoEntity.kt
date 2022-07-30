package com.spcswabapp.content.new_anexok.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tips_analisisderiesgo")
data class TipsAnalisisRiesgoEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idtips_analisisderiesgo") var idtips_analisisderiesgo : Int = 0,
    @ColumnInfo(name = "tips_analisisderiesgo") var tips_analisisderiesgo : String,
    @ColumnInfo(name = "grupo") var grupo : String,
    @ColumnInfo(name = "activo") var activo : Int,

)
