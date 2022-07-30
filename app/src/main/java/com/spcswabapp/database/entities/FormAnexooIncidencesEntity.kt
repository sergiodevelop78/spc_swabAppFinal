package com.spcswabapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "form_anexoo_registros")
data class FormAnexooIncidencesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idform_anexoo_registros") val idRegistro: Int = 0,

    @ColumnInfo(name = "idform_anexoo") val idFormAnexoo: Int,
    @SerializedName("km_inicial")
    @ColumnInfo(name = "km_inicial") var kmInicial: String,
    @SerializedName("km_final")
    @ColumnInfo(name = "km_final") var kmFinal: String,
    @SerializedName("horometro_inicial")
    @ColumnInfo(name = "horometro_inicial") var horometroInicial: String,
    @SerializedName("horometro_final")
    @ColumnInfo(name = "horometro_final") var horometroFinal: String,
    @SerializedName("equipo")
    @ColumnInfo(name = "equipo") var equipo: String,
    @SerializedName("origen")
    @ColumnInfo(name = "origen") var origen: String,
    @SerializedName("destino")
    @ColumnInfo(name = "destino") var destino: String,
    @SerializedName("hora_inicial")
    @ColumnInfo(name = "hora_inicial") var horaInicial: String,
    @SerializedName("hora_final")
    @ColumnInfo(name = "hora_final") var horaFinal: String,
    @SerializedName("oil")
    @ColumnInfo(name = "oil") var oil: String,
    @SerializedName("agua")
    @ColumnInfo(name = "agua") var agua: String,
    @SerializedName("s_t")
    @ColumnInfo(name = "s_t") var s_t: String,
    @SerializedName("npc_roto")
    @ColumnInfo(name = "npc_roto") var npcRoto: String,
    @SerializedName("npc_nuevo")
    @ColumnInfo(name = "npc_nuevo") var npcNuevo: String,
    @SerializedName("npe_roto")
    @ColumnInfo(name = "npe_roto") var npeRoto: String,
    @SerializedName("npe_nuevo")
    @ColumnInfo(name = "npe_nuevo") var npeNuevo: String,

    @SerializedName("tanque_descarga_zona")
    @ColumnInfo(name = "tanque_descarga_zona") var tanque_descarga_zona: String,

    @SerializedName("observaciones")
    @ColumnInfo(name = "observaciones") var observaciones: String,



    )
