package com.spcswabapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "swab_reporte")
data class SwabReportEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idswab_reporte") var idSwabReporte: Int = 0,

    @ColumnInfo(name = "fecha_registro") val fechaRegistro: String,

    @ColumnInfo(name = "idestado_swab") var idEstadoSwab: Int,
    @SerializedName("idusuario")
    @ColumnInfo(name = "idusuario") val idUsuario: Int,
    @SerializedName("idequipo")
    @ColumnInfo(name = "idequipo") val idEquipo: Int,
    @SerializedName("idturno")
    @ColumnInfo(name = "idturno") val idTurno: Int,
    @SerializedName("comentarios")
    @ColumnInfo(name = "comentarios") var comentarios: String?,
    @SerializedName("csms")
    @ColumnInfo(name = "csms") var csms: String?,

    @ColumnInfo(name = "fecha_cierre") val fechaCierre: String,
    @ColumnInfo(name = "idswab_produccion") val idswab_produccion: String
    
)

