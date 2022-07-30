package com.spcswabapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity( tableName = "swab_combustible")
data class SwabFuelEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idswab_combustible") val idSwabCombustible: Int = 0,
    @SerializedName("idswab_reporte")
    @ColumnInfo(name = "idswab_reporte") var idSwabReporte: Int,

    @ColumnInfo(name = "galones") var galones: String,

    @ColumnInfo(name = "hora") var hora: String,
    @SerializedName("horometro_ini")
    @ColumnInfo(name = "horometro_ini") var horometroIni: String,
    @SerializedName("horometro_fin")
    @ColumnInfo(name = "horometro_fin") var horometroFin: String,
    @SerializedName("observaciones")
    @ColumnInfo(name = "observaciones") var observaciones: String,

    )

