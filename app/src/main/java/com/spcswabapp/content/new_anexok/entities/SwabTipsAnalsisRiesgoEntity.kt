package com.spcswabapp.content.new_anexok.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "swab_tips_analisisderiesgo")
data class SwabTipsAnalsisRiesgoEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idswab_tips_analisisderiesgo") var idswab_tips_analisisderiesgo : Int = 0,

    @ColumnInfo(name = "idregistro_incidencia") var idregistro_incidencia : Int,
    @ColumnInfo(name = "idtips_analisisderiesgo") var idtips_analisisderiesgo : Int,
    @ColumnInfo(name = "tips_analisisderiesgo") var tips_analisisderiesgo : String,
    @ColumnInfo(name = "grupo") var grupo : String,
    @ColumnInfo(name = "estado") var estado : Int,

    )