package com.spcswabapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "revisiones_diarias")
data class RevisionesDiariasEntity (

    @PrimaryKey(autoGenerate = true)
    @SerializedName("idrevisiones_diarias")
    @ColumnInfo(name = "idrevisiones_diarias") val idRevisionesDiarias : Int,

    @SerializedName("revision_diaria")
    @ColumnInfo(name = "revision_diaria") var revision_diaria : String,

    @SerializedName("activo")
    @ColumnInfo(name = "activo") var activo : Int,

)