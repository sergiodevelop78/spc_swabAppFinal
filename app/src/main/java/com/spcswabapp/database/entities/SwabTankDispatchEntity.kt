package com.spcswabapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "swab_despacho_cisternas")
data class SwabTankDispatchEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idswab_despacho_cisternas") val idSwabDespachoCisternas: Int = 0,

    @SerializedName("idswab_reporte")
    @ColumnInfo(name = "idswab_reporte") var idSwabReporte: Int,
    @SerializedName("nro_cisterna")
    @ColumnInfo(name = "nro_cisterna") var nroCisterna: String = "0",
    @SerializedName("hora_salida")
    @ColumnInfo(name = "hora_salida") var horaSalida: String,
    @SerializedName("pozo1")
    @ColumnInfo(name = "pozo1") var pozo1: String,
    @SerializedName("cantidad_despachada")
    @ColumnInfo(name = "cantidad_despachada") var cantidadDespachada: String,
    @SerializedName("lugar_de_descarga")
    @ColumnInfo(name = "lugar_de_descarga") var lugarDeDescarga: String,
    @SerializedName("cisterna")
    @ColumnInfo(name = "cisterna") var cisterna: String,
    @SerializedName("procedencia")
    @ColumnInfo(name = "procedencia") var procedencia: String,
    @SerializedName("hora_llegada")
    @ColumnInfo(name = "hora_llegada") var horaLlegada: String,
    @SerializedName("pozo2")
    @ColumnInfo(name = "pozo2") var pozo2: String,

    )