package com.spcswabapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "swab_puntos_criticos")
data class SwabCriticalPointEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idswab_pc") val idSwabPc: Int = 0,

    @ColumnInfo(name = "idswab_reporte") var idSwabReporte : Int,
    @SerializedName("idpc")
    @ColumnInfo(name = "idpunto_critico") val idPuntoCritico : Int,
    @ColumnInfo(name = "tipo") var tipo : String,
    @ColumnInfo(name = "nombre") var nombre : String,
    @SerializedName("estadopc")
    @ColumnInfo(name = "estadopc") var estadoPc : Int,
    @SerializedName("observaciones")
    @ColumnInfo(name = "observaciones") var observaciones : String,

    )