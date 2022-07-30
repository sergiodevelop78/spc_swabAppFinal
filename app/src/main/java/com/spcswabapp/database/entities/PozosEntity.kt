package com.spcswabapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pozos")
data class PozosEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "idpozo") var idpozo : Int,

    @ColumnInfo(name = "pozo") var pozo : String,
    @ColumnInfo(name = "bateria") var bateria : String,
    @ColumnInfo(name = "diametro") var diametro : String,
    @ColumnInfo(name = "diametro_fraccion") var diametro_fraccion : String,
    @ColumnInfo(name = "na_td") var na_td : String
)


