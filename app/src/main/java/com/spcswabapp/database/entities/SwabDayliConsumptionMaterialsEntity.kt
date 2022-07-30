package com.spcswabapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "swab_materiales_consumo")
data class SwabDayliConsumptionMaterialsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idswab_materiales_consumo") val idswabMaterialesConsumo: Int = 0,

    @SerializedName("idswab_reporte")
    @ColumnInfo(name = "idswab_reporte") var idswabReporte: Int,

    @SerializedName("mnr_copa1")
    @ColumnInfo(name = "mnr_copa1") var mnrCopa1: String,
    @SerializedName("mnr_copa2")
    @ColumnInfo(name = "mnr_copa2") var mnrCopa2: String,
    @SerializedName("mnr_copa3")
    @ColumnInfo(name = "mnr_copa3") var mnrCopa3: String,
    @SerializedName("mnr_copa4")
    @ColumnInfo(name = "mnr_copa4") var mnrCopa4: String,
    @SerializedName("mnr_eco")
    @ColumnInfo(name = "mnr_eco") var mnrEco: String,
    @SerializedName("ms_copa1")
    @ColumnInfo(name = "ms_copa1") var msCopa1: String,
    @SerializedName("ms_copa2")
    @ColumnInfo(name = "ms_copa2") var msCopa2: String,
    @SerializedName("ms_copa3")
    @ColumnInfo(name = "ms_copa3") var msCopa3: String,
    @SerializedName("ms_copa4")
    @ColumnInfo(name = "ms_copa4") var msCopa4: String,
    @SerializedName("ms_eco")
    @ColumnInfo(name = "ms_eco") var msEco: String,
    @SerializedName("mnd_copa1")
    @ColumnInfo(name = "mnd_copa1") var mndCopa1: String,
    @SerializedName("mnd_copa2")
    @ColumnInfo(name = "mnd_copa2") var mndCopa2: String,
    @SerializedName("mnd_copa3")
    @ColumnInfo(name = "mnd_copa3") var mndCopa3: String,
    @SerializedName("mnd_copa4")
    @ColumnInfo(name = "mnd_copa4") var mndCopa4: String,
    @SerializedName("mnd_eco")
    @ColumnInfo(name = "mnd_eco") var mndEco: String,
    @SerializedName("mda_copa1")
    @ColumnInfo(name = "mda_copa1") var mdaCopa1: String,
    @SerializedName("mda_copa2")
    @ColumnInfo(name = "mda_copa2") var mdaCopa2: String,
    @SerializedName("mda_copa3")
    @ColumnInfo(name = "mda_copa3") var mdaCopa3: String,
    @SerializedName("mda_copa4")
    @ColumnInfo(name = "mda_copa4") var mdaCopa4: String,
    @SerializedName("mda_eco")
    @ColumnInfo(name = "mda_eco") var mdaEco: String,

    )