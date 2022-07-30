package com.spcswabapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "form_anexoo_revisiones_diarias")
data class FormAnexooRevisionesDiariasEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,

    @ColumnInfo(name = "idform_anexoo") var idFormAnexoo : Int,
    @SerializedName("idrevisiones_diarias")
    @ColumnInfo(name = "idrevisiones_diarias") val idRevisionesDiarias : Int,
    @ColumnInfo(name = "tipo") var tipo : String,
    @ColumnInfo(name = "nombre") var nombre : String,
    @ColumnInfo(name = "estado") var estado : Int,
    @SerializedName("observaciones")
    @ColumnInfo(name = "observaciones") var observaciones : String,

    )