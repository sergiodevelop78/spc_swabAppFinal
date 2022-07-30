package com.spcswabapp.content.new_anexok.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class TipsAnalisisRiesgoModel (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idtips_analisisderiesgo") var idtips_analisisderiesgo : Int = 0,
    @ColumnInfo(name = "tips_analisisderiesgo") var tips_analisisderiesgo : String,
    @ColumnInfo(name = "grupo") var grupo : String,
    @ColumnInfo(name = "estado") var estado : Int,

    )
