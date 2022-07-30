package com.spcswabapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "form_anexoo")
data class FormAnexooReportEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idform_anexoo") var idFormAnexoo: Int = 0,

    @ColumnInfo(name = "fecha_registro") val fechaRegistro: String,

    @ColumnInfo(name = "idestado") var idEstado: Int,

    @SerializedName("idusuario")
    @ColumnInfo(name = "idusuario") val idUsuario: Int,

    @SerializedName("cisterna")
    @ColumnInfo(name = "cisterna") var cisterna: String,

    @SerializedName("fecha")
    @ColumnInfo(name = "fecha") var fecha: String,

    @SerializedName("idturno")
    @ColumnInfo(name = "idturno") val idTurno: Int,

    @SerializedName("hora")
    @ColumnInfo(name = "hora") var hora: String,

    @SerializedName("lugar_relevo")
    @ColumnInfo(name = "lugar_relevo") var lugarRelevo: String?,

    @ColumnInfo(name = "volumen") var volumen: String,

    @SerializedName("nro_precinto")
    @ColumnInfo(name = "nro_precinto") var nroPrecinto: String,

    @SerializedName("macs_obs")
    @ColumnInfo(name = "macs_obs") var macsObs: String?,

    @SerializedName("idform_anexoo_produccion")
    @ColumnInfo(name = "idform_anexoo_produccion") var idFormAnexoProduccion: String,

    )

